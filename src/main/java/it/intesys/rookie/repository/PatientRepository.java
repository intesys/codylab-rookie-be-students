package it.intesys.rookie.repository;

import it.intesys.rookie.domain.Doctor;
import it.intesys.rookie.domain.Patient;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

@Repository

public class PatientRepository {
    private final JdbcTemplate db;

    public PatientRepository(JdbcTemplate db) {
        this.db = db;
    }
    public Patient save(Patient patient) {
        if (patient.getId() == null) {
            Long id = db.queryForObject("select nextval('patient_sequence')", Long.class);
            patient.setId(id);
            db.update("insert into patient (id, name, surname, email, phoneNumber, lastAdmission) " +
                            "values(?, ?, ?, ?, ?, ?)", patient.getId(), patient.getName(), patient.getSurname(), patient.getEmail(),
                    patient.getPhoneNumber(), Timestamp.from(patient.getLastAdmission()));
            return patient;
        } else {
            db.update("update patient set lastAdmission = ?, name = ?, surname = ?, email = ?, phoneNumber = ? where id = ?", Timestamp.from(patient.getLastAdmission()), patient.getName(), patient.getSurname(),
                    patient.getEmail(), patient.getPhoneNumber(), patient.getId());
            return findPatientById(patient.getId());
        }
    }

    public Optional<Patient> findById(Long id) {
        try {
            Patient patient = db.queryForObject("select * from patient where id = ?", this::map, id);
            return Optional.ofNullable(patient);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();

        }
    }
    private Patient findPatientById(Long id) {
        Patient patient = db.queryForObject("select * from patient where id = ?", this::map, id);
        return patient;
    }

    private Patient map(ResultSet resultSet, int i) throws SQLException {
        Patient patient = new Patient();
        patient.setId(resultSet.getLong("id"));
        patient.setName(resultSet.getString("name"));
        patient.setSurname(resultSet.getString("surname"));
        patient.setEmail(resultSet.getString("email"));
        patient.setPhoneNumber(resultSet.getLong("phonenumber"));
        patient.setLastAdmission(resultSet.getTimestamp("lastadmission").toInstant());
        return patient;
    }
    public Optional<Patient> deleteById(Long id) {
        try {
            Patient patient = db.queryForObject("select * from patient where id = ?", this::map, id);
            db.update("delete from patient where id = ?",id);
            return Optional.ofNullable(patient);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();

        }
    }
}
