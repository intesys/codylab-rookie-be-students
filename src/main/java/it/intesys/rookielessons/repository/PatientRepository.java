package it.intesys.rookielessons.repository;

import it.intesys.rookielessons.domain.Patient;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PatientRepository extends HospitalRepository {
    public PatientRepository(JdbcTemplate db){
        super(db);
    }

    public Patient save(Patient patient) {
        if(patient.getId() == null){
            Long id = db.queryForObject("select nextval('patient_sequence') ", Long.class);
            patient.setId(id);
            db.update("insert into patient (id, last_admission, phone_number, name, surname, email) " +
                            "values (?, ?, ?, ?, ?, ?)", patient.getId(), Timestamp.from(patient.getLastAdmission()), patient.getPhoneNumber(),
                    patient.getName(), patient.getSurname(), patient.getEmail());
            return patient;
        } else {
            int updateCount = db.update("update patient set last_admission = ?, name = ?, surname = ?, email = ?, " +
                            "status = ? where id = ?", Timestamp.from(patient.getLastAdmission()), patient.getPhoneNumber(), patient.getName(),
                    patient.getSurname(), patient.getEmail(), patient.getId());
            if(updateCount != 1){
                throw new IllegalStateException(String.format("Update count %d, excepted 1", updateCount));
            }
            return findOriginalPatientById(patient.getId());
        }
    }

    public Optional<Patient> findById(Long id) {
        try{
            Patient patient = db.queryForObject("select * from patient where id = ?", this::map, id);
            return Optional.ofNullable(patient);
        } catch (EmptyResultDataAccessException e){
            logger.warn(e.getMessage());
            return Optional.empty();
        }
    }

    private Patient findOriginalPatientById(Long id) {
        return db.queryForObject("select * from patient where id = ?", this::map, id);
    }

    public void deletePatient(Long id){
        int updateCount = db.update("delete from patient where id = ?", id);

        if(updateCount != 1){
            throw new IllegalStateException(String.format("Update count %d, excepted 1", updateCount));
        } else System.out.println("DELETE SUCCESS\nUtente con id = " + id);
    }

    private Patient map(ResultSet resultSet, int i) throws SQLException {
        Patient patient = new Patient();
        patient.setId(resultSet.getLong("id"));
        patient.setLastAdmission(Optional.ofNullable(resultSet.getTimestamp("last_admission")).map(Timestamp::toInstant).orElse(null));
        patient.setPhoneNumber(resultSet.getLong("phone_number"));
        patient.setName(resultSet.getString("name"));
        patient.setSurname(resultSet.getString("surname"));
        patient.setEmail(resultSet.getString("email"));

        return patient;
    }

    public Page<Patient> findAll(String filter, Pageable pageable) {
        StringBuilder queryBuffer = new StringBuilder("select * from patient ");
        List<Object> parameters = new ArrayList<>();
        if (filter != null && !filter.isBlank()) {
            queryBuffer.append("where name like ? or surname like ? or email like ?");
            String like = "%" + filter + "%";
            for (int i = 0; i < 4; i++) parameters.add(like);
        }
        String query = pagingQuery(queryBuffer, pageable);
        List<Patient> patients = db.query(query, this::map, parameters.toArray());
        return new PageImpl<>(patients, pageable, 0);
    }
}
