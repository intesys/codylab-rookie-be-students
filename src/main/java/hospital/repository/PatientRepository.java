package hospital.repository;

import hospital.domain.Patient;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class PatientRepository {
    private final JdbcTemplate db;

    public PatientRepository(JdbcTemplate db) {
        this.db = db;
    }

    public Patient save(Patient patient) {
        Long id = db.queryForObject("select nextval(account_sequence) ", Long.class);
        patient.setId(id);
        db.update ("insert into account (id, address, idp, opd, phoneNumber, notes, chronicPatient, lastAdmission, lastDoctorVisitedId, patientRecords, doctorIds, name, surname, email)" +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                patient.getId(),
                patient.getAddress(),
                patient.getIdp(),
                patient.getOpd(),
                patient.getPhoneNumber(),
                patient.getNotes(),
                patient.getChronicPatient(),
                patient.getLastAdmission(),
                patient.getLastDoctorVisitedId(),
                patient.getPatientRecords(),
                patient.getDoctorIds(),
                patient.getAvatar(),
                patient.getName(),
                patient.getSurname(),
                patient.getEmail());
        return patient;
    }

    public Optional<Patient> findById(Long id) {
        try {
            Patient patient = db.queryForObject("select * from patient where id = ?", this::map, id);
            return Optional.ofNullable(patient);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }

    }

    private Patient map (ResultSet resultSet, int i) throws SQLException {
        Patient patient = new Patient();
        patient.setId(resultSet.getLong("id"));
        patient.setAddress(resultSet.getString("address"));
        patient.getIdp(resultSet.getLong("idp"));
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
}
