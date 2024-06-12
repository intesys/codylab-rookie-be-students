package it.intesys.rookie.repository;

import it.intesys.rookie.domain.Patient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public class PatientRepository {
    private final JdbcTemplate db;

    public PatientRepository(JdbcTemplate db){
        this.db = db;
    }

    public Patient save(Patient patient) {
        Long id = db.queryForObject("select nextval('patient_sequence') ", Long.class);
        patient.setId(id);
        db.update("insert into patient (id, phone_number, last_admission, name, surname, email) " +
                "values (?, ?, ?, ?, ?, ?)", patient.getId(), patient.getPhoneNumber(), Timestamp.from(patient.getLastAdmission()), patient.getName(), patient.getSurname(), patient.getEmail());
        return patient;
    }
}
