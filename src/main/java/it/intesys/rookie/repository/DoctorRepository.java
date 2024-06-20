package it.intesys.rookie.repository;

import it.intesys.rookie.domain.Doctor;
import it.intesys.rookie.domain.Patient;
import it.intesys.rookie.utilities.Utilities;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DoctorRepository extends RookieRepository{

    private final PatientRepository patientRepository;

    public DoctorRepository(JdbcTemplate db, PatientRepository patientRepository){
        super(db);
        this.patientRepository = patientRepository;
    }

    public Doctor save(Doctor doctor) {
        if(doctor.getId() == null){
            Long id = db.queryForObject("select nextval('doctor_sequence') ", Long.class);
            doctor.setId(id);
            db.update("insert into doctor (id, name, surname, phone_number, address, email, avatar, profession) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?)", doctor.getId(), doctor.getName(), doctor.getSurname(), doctor.getPhoneNumber(), doctor.getAddress(), doctor.getEmail(), doctor.getAvatar(), doctor.getProfession());

            return doctor;
        }  else {
            db.update("update doctor set name = ?, surname = ?, phone_number = ?, address = ?, email = ?, avatar = ?, profession = ? where id = ?", doctor.getName(), doctor.getSurname(), doctor.getPhoneNumber(), doctor.getAddress(), doctor.getEmail(), doctor.getAvatar(), doctor.getProfession(), doctor.getId());

            List<Patient> patients = doctor.getPatients();
            List<Patient> currentPatients = findDoctorById(doctor.getId()).getPatients();


            List<Patient> insertions = subtract(patients, currentPatients);
            db.batchUpdate("insert into doctor_patient (doctor_id, patient_id) values (?, ?)", insertions, 100, (ps, patient) -> {
                ps.setLong(1, doctor.getId());
                ps.setLong(2, patient.getId());
            });

            List<Patient> deletions = subtract(currentPatients, patients);
            db.batchUpdate("delete from doctor_patient where doctor_id = ? and patient_id = ?", deletions, 100, (ps, account) -> {
                ps.setLong(1, doctor.getId());
                ps.setLong(2, account.getId());
            });
            return findDoctorById(doctor.getId());
         }
    }

    public Optional<Doctor> findById(Long id) {
        try{
            Doctor doctor = findDoctorById(id);
            return Optional.ofNullable(doctor);
        } catch (EmptyResultDataAccessException e){
            logger.warn(e.getMessage());
            return Optional.empty();
        }
    }

    private Doctor findDoctorById(Long id) {
        Doctor doctor = db.queryForObject("select * from doctor where id = ?", this::map, id);
        if (doctor != null) {
            List<Patient> patients = patientRepository.findByDoctorId(id);
            doctor.setPatients(patients);
        }
        return doctor;
    }

    public Optional<Doctor> deleteDoctor(Long id){
        try{
            Doctor doctor = db.queryForObject("select * from doctor where id = ?", this::map, id);
            db.update("delete from doctor_patient where doctor_id = ?", id);
            db.update("update patient_record set doctor_id = NULL where id = ?", id);
            db.update("delete from doctor where id = ?", id);
            logger.debug("DELETE SUCCESS\nDoctor con id = " + id);
            return Optional.ofNullable(doctor);
        } catch (EmptyResultDataAccessException e){
            logger.debug("DELETE ERROR\nDoctor con id = " + id);
            return Optional.empty();
        }
    }

    private Doctor map(ResultSet resultSet, int i) throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setId(resultSet.getLong("id"));
        doctor.setPhoneNumber(resultSet.getLong("phone_number"));
        doctor.setName(resultSet.getString("name"));
        doctor.setSurname(resultSet.getString("surname"));
        doctor.setEmail(resultSet.getString("email"));
        doctor.setProfession(resultSet.getString("profession"));
        doctor.setAvatar(resultSet.getString("avatar"));
        doctor.setAddress(resultSet.getString("address"));
        return doctor;
    }

    public Page<Doctor> findAll(String name, String surname, String profession, Pageable pageable) {
        String whereOrAnd = "where ";
        StringBuilder queryBuffer = new StringBuilder("select * from doctor ");
        List<Object> parameters = new ArrayList<>();

        if (name != null && !name.isBlank()) {
            queryBuffer.append(whereOrAnd).append("name like ? ");
            whereOrAnd = "and ";
            parameters.add("%" + name + "%");
        }
        if (surname != null && !surname.isBlank()) {
            queryBuffer.append(whereOrAnd).append("surname like ? ");
            whereOrAnd = "and ";
            parameters.add("%" + surname + "%");
        }
        if (profession != null && !profession.isBlank()) {
            queryBuffer.append(whereOrAnd).append("profession like ?");
            whereOrAnd = "and ";
            parameters.add("%" + profession + "%");
        }

        String query = Utilities.pagingQuery(queryBuffer, pageable);
        List<Doctor> doctors = db.query(query, this::map, parameters.toArray());
        return new PageImpl<>(doctors, pageable, 0);
    }
}
