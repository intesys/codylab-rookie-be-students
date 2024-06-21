package it.intesys.openhospital.repository;

import it.intesys.openhospital.domain.Doctor;
import it.intesys.openhospital.domain.Patient;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DoctorRepository extends CommonRepository {
    private final PatientRepository patientRepository;

    public DoctorRepository(JdbcTemplate db, PatientRepository patientRepository) {
        super(db);
        this.patientRepository = patientRepository;
    }


    public Doctor save(Doctor doctor) {
        if (doctor.getId() == null) {
            Long id = db.queryForObject("select nextval('doctor_sequence')", Long.class);
            doctor.setId(id);
            db.update("Insert into doctor (id, name, surname, phonenumber, address, email, avatar, profession)" +
                            "values(?, ?, ?, ?, ?, ?, ?, ?)", doctor.getId(), doctor.getName(), doctor.getSurname(), doctor.getPhoneNumber(), doctor.getAddress(), doctor.getEmail(),
                    doctor.getAvatar(), doctor.getProfession());
            return doctor;
        } else {
            int updateCount = db.update("update doctor set name = ?, surname = ?, phonenumber = ?, address = ?, email = ?, avatar = ?, profession = ? where id = ?", doctor.getName(), doctor.getSurname(), doctor.getPhoneNumber(), doctor.getAddress(), doctor.getEmail(),
                    doctor.getAvatar(), doctor.getProfession(), doctor.getId());
            if (updateCount != 1){
                throw new IllegalStateException(String.format("Update count %d, expected 1", updateCount));
            }
            List<Patient> patients = doctor.getPatients();
            List<Patient> currentPatients = getDoctor(doctor.getId()).getPatients();


            List<Patient> insertions = subtract(patients, currentPatients);
            db.batchUpdate("insert into doctor_patient (doctor_id, patient_id) values (?, ?)", insertions, BATCH_SIZE, (ps, patient) -> {
                ps.setLong(1, doctor.getId());
                ps.setLong(2, patient.getId());
            });

            List<Patient> deletions = subtract(currentPatients, patients);
            db.batchUpdate("delete from doctor_patient where doctor_id = ? and patient_id = ?", deletions, BATCH_SIZE, (ps, account) -> {
                ps.setLong(1, doctor.getId());
                ps.setLong(2, account.getId());
            });
            return getDoctor(doctor.getId());
        }
    }

    public Optional<Doctor> findById(Long id) {
        try {
            Doctor doctor = getDoctor(id);
            return Optional.ofNullable(doctor);
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    private Doctor getDoctor(Long id) {
        Doctor doctor = db.queryForObject("select * from doctor where id = ?", this::map, id);
        if (doctor != null) {
            List<Patient> patients = patientRepository.findByDoctorId(id);
            doctor.setPatients(patients);
        }
        return doctor;
    }

    private Doctor map(ResultSet resultSet, int i) throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setId(resultSet.getLong("id"));
        doctor.setName(resultSet.getString("name"));
        doctor.setSurname(resultSet.getString("surname"));
        doctor.setPhoneNumber(resultSet.getLong("phonenumber"));
        doctor.setAddress(resultSet.getString("address"));
        doctor.setEmail(resultSet.getString("email"));
        doctor.setAvatar(resultSet.getString("avatar"));
        doctor.setProfession(resultSet.getString("profession"));
        return doctor;

    }

    public Page<Doctor> findAll(String name, String surname, String profession, Pageable pageable) {
        StringBuilder queryBuffer = new StringBuilder("select * from doctor ");
        List<Object> parameters = new ArrayList<>();
        String whereAndOr = "where";
        if (name != null){
            queryBuffer.append(whereAndOr);
            whereAndOr = "and";
            queryBuffer.append(" name like ? ");
            parameters.add("%" + name + "%");
        }

        if (surname != null){
            queryBuffer.append(whereAndOr);
            whereAndOr = "and";
            queryBuffer.append(" surname like ? ");
            parameters.add("%" + surname + "%");
        }

        if (profession != null){
            queryBuffer.append(whereAndOr);
            queryBuffer.append(" profession like ? ");
            parameters.add("%" + profession + "%");
        }
        String query = pagingQuery(queryBuffer, pageable);
        List<Doctor> doctors = db.query(query, this::map, parameters.toArray());
        return new PageImpl<>(doctors, pageable, 0);
    }
    public Page<Doctor> findLatestByPatient(Patient patient, int size){
        StringBuilder queryBuffer = new StringBuilder("select doctor.* from patient_record" +
                " join doctor on doctor.id = patient_record.doctorid" +
                " where patient_record.patientid = ?");

        List<Object> parameters = List.of(patient.getId());

        PageRequest pageable = PageRequest.of(0, size, Sort.by(Sort.Order.desc("date")));
        String query = pagingQuery(queryBuffer, pageable);

        List<Doctor> doctors = db.query(query, this::map, parameters.toArray(Object[]::new));
        return new PageImpl<>(doctors, pageable, 0);
    }


    public void delete(Long id) {
        int updateCount = db.update("delete from doctor where id = ?", id);
        if (updateCount != 1){
            throw new IllegalStateException(String.format("Update count %d, expected 1", updateCount));
        }
    }

}