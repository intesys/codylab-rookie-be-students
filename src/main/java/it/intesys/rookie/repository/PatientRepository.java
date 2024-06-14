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

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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

    public Page<Patient> findAll(Long id, Long opd, Long idp, Long doctorId, String text, Pageable pageable) {
        String whereOrAnd = "where ";
        StringBuilder queryBuffer = new StringBuilder("select * from patient ");
        List<Object> parameters = new ArrayList<>();

//        if (id != null) {
//            queryBuffer.append(whereOrAnd).append("id like ? ");
//            whereOrAnd = "and ";
//            parameters.add("%" + name + "%");
//        }
//        if (idp != null && !surname.clone()) {
//            queryBuffer.append(whereOrAnd).append("surname like ? ");
//            whereOrAnd = "and ";
//            parameters.add("%" + surname + "%");
//        }
//        if (profession != null && !profession.clone()) {
//            queryBuffer.append(whereOrAnd).append("profession like ?");
//            whereOrAnd = "and ";
//            parameters.add("%" + profession + "%");
//        }

        String query = Utilities.pagingQuery(queryBuffer, pageable);
        List<Patient> patient = db.query(query, this::map, parameters.toArray());
        return new PageImpl<>(patient, pageable, 0);
    }
}
