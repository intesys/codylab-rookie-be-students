package it.intesys.rookie.repository;

import it.intesys.rookie.domain.Doctor;
import it.intesys.rookie.domain.Patient;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

@Repository
public class PatientRepository {
    private final JdbcTemplate db;

    public PatientRepository(JdbcTemplate db) throws SQLException {
        this.db = db;
    }

    public Patient save(Patient patient) {
        if(patient.getId() == null){ //SE NON ESISTE
            Long id = db.queryForObject("select nextval('patient_sequence') ", Long.class);
            patient.setId(id);
            db.update("insert into patient (id, phone_number, last_admission, name, surname, email) " +
                    "values (?, ?, ?, ?, ?, ?)", patient.getId(), patient.getPhoneNumber(), Timestamp.from(patient.getLastAdmission()), patient.getName(), patient.getSurname(), patient.getEmail());
            return patient;
        }  else {
            db.update("update patient set phone_number = ?, name = ?, surname = ?, email = ? where id = ?", patient.getPhoneNumber(), patient.getName(), patient.getSurname(), patient.getEmail(), patient.getId());
            return findOriginalAccountById(patient.getId());
        }
    }

    public Optional<Patient> findById(Long id) {
        try{
            Patient patient = db.queryForObject("select * from patient where id = ?", this::map, id);
            return Optional.ofNullable(patient);
        } catch (EmptyResultDataAccessException e){
            System.out.println("FOUND ERROR\nPatient with id = " + id);
            return Optional.empty();
        }
    }

    private Patient findOriginalAccountById(Long id) {
        Patient patient = db.queryForObject("select * from patient where id = ?", this::map, id);
        return patient;
    }

    public Optional<Patient> deletePatient(Long id) {
        try{
            Patient patient = db.queryForObject("select * from patient where id = ?", this::map, id);
            db.update("delete from patient where id = ?", id);
            System.out.println("DELETE SUCCESS\nPatient con id = " + id);
            return Optional.ofNullable(patient);
        } catch (EmptyResultDataAccessException e){
            System.out.println("DELETE ERROR\nPatient con id = " + id);
            return Optional.empty();
        }
    }

    private Patient map(ResultSet resultSet, int i) throws SQLException {
        Patient patient = new Patient();
        patient.setId(resultSet.getLong("id"));
        patient.setPhoneNumber(resultSet.getLong("phone_number"));
        patient.setLastAdmission(resultSet.getTimestamp("last_admission").toInstant());
        patient.setName(resultSet.getString("name"));
        patient.setSurname(resultSet.getString("surname"));
        patient.setEmail(resultSet.getString("email"));
        return patient;
    }

}
