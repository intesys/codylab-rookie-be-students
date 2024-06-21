package hospital.repository;

import hospital.domain.Doctor;
import hospital.domain.Patient;
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
public class PatientRepository extends CommonRepository{
    public PatientRepository(JdbcTemplate db) {
        super(db);
    }

    public Patient save(Patient patient) {
        if (patient.getId() == null) {
            Long id = db.queryForObject("select nextval('patient_sequence')", Long.class);
            patient.setId(id);
            db.update("Insert into patient (id, opd, idp, name, surname, phoneNumber, address, email, avatar, bloodGroup, notes, chronicPatient, LastAdmission, lastDoctorVisitedId)" +
                            "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", patient.getId(), patient.getOpd(), patient.getIdp(), patient.getName(), patient.getSurname(), patient.getPhoneNumber(), patient.getAddress(), patient.getEmail(),
                    patient.getAvatar(), patient.getBloodGroup().ordinal(), patient.getNotes(), patient.getChronicPatient(), patient.getLastAdmission(), patient.getLastDoctorVisitedId());
            return patient;
        } else {
            int updateCount = db.update("update patient set opd = ?, idp = ?, name = ?, surname= ?, phoneNumber = ?, address = ?, email = ?, avatar = ?, bloodGroup = ?, notes = ?, chronicPatient = ?,  lastDoctorVisitedId = ?, where id = ?", patient.getOpd(), patient.getIdp(), patient.getName(), patient.getSurname(), patient.getPhoneNumber(), patient.getAddress(), patient.getEmail(),
                    patient.getAvatar(), patient.getBloodGroup().ordinal(), patient.getNotes(), patient.getChronicPatient(), patient.getLastDoctorVisitedId(), patient.getId());
            if (updateCount != 1){
                throw new IllegalStateException(String.format("Update count %d, excepted 1", updateCount));
            }
            return getPatient(patient.getId());
        }
    }

    public Optional<Patient> findById(Long id) {
        try {
            Patient patient = getPatient(id);
            return Optional.ofNullable(patient);
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    protected Patient getPatient(Long id) {
        Patient patient = db.queryForObject("select * from patient where id = ?", this::map, id);
        return patient;
    }

    private Patient map (ResultSet resultSet, int i) throws SQLException {
        Patient patient = new Patient();
        patient.setId(resultSet.getLong("id"));
        patient.setAddress(resultSet.getString("address"));
        patient.setIdp(resultSet.getLong("idp"));
        patient.setOpd(resultSet.getLong("odp"));
        patient.setPhoneNumber(resultSet.getLong("phone_number"));
        patient.setNotes(resultSet.getString("notes"));
        patient.setChronicPatient(resultSet.getBoolean("chronic_patient"));
        patient.setLastAdmission(resultSet.getString("last_admission"));
        patient.setLastDoctorVisitedId(resultSet.getString("last_doctor_visited"));
        patient.setPatientRecords((List)resultSet.getObject("patient_records"));
        patient.setDoctorIds(resultSet.getLong("doctors_id"));
        patient.setAvatar(resultSet.getString("avatar"));
        patient.setName(resultSet.getString("name"));
        patient.setSurname(resultSet.getString("surname"));
        patient.setEmail(resultSet.getString("email"));
        return patient;

    }

    public void delete(Long id) {
    }

    public Page<Patient> findAll(String filter, Long id, Long opd, Long idp, Long doctorId, String text, Pageable pageable) {
        StringBuilder queryBuffer = new StringBuilder("select * from patient a ");
        List<Object> parameters = new ArrayList<>();
        String whereAndOr = "where ";
        if (doctorId != null){
            queryBuffer.append("join doctor_patient b on b.patientid = a.id ") .append(whereAndOr);
            whereAndOr = "and ";
            queryBuffer.append("b.doctorid = ? ");
            parameters.add(doctorId);
        }

        if (text != null){
            queryBuffer.append(whereAndOr);
            whereAndOr = "and ";
            queryBuffer.append("name like ? or surname like ? or address like ? or email like ? or avatar like ? or notes like ?");
            for (int i = 0; i < 6; i++) parameters.add("%" + text + "%");
        }

        if (id != null){
            queryBuffer.append(whereAndOr);
            whereAndOr = "and ";
            queryBuffer.append("id = ? ");
            parameters.add(id);
        }

        if (opd != null){
            queryBuffer.append(whereAndOr);
            whereAndOr = "and ";
            queryBuffer.append("opd = ? ");
            parameters.add(opd);
        }

        if (idp != null){
            queryBuffer.append(whereAndOr);
            whereAndOr = "and ";
            queryBuffer.append("idp = ? ");
            parameters.add(idp);
        }

        String query = pagingQuery(queryBuffer, pageable);
        List<Patient> patients = db.query(query, this::map, parameters.toArray());
        return new PageImpl<>(patients, pageable, 0);
    }
    public List<Patient> findByDoctorId(Long doctorId) {
        return db.query("select patient.* from doctor_patient " +
                "join patient on doctor_patient.patient_id = patient.id " +
                "where doctor_patient.doctor_id = ?", this::map, doctorId);
    }

    public Page<Patient> findLatestByDoctor(Doctor doctor, int size){
        StringBuilder queryBuffer = new StringBuilder("select patient.* from patient_record" +
                " join patient on patient.id = patient_record.patientid" +
                " where patient_record.doctorid = ?");

        List<Object> parameters = List.of(doctor.getId());

        PageRequest pageable = PageRequest.of(0, size, Sort.by(Sort.Order.desc("date")));
        String query = pagingQuery(queryBuffer, pageable);

        List<Patient> patients = db.query(query, this::map, parameters.toArray(Object[]::new));
        return new PageImpl<>(patients, pageable, 0);
    }
}
