package it.intesys.repository;

import it.intesys.domain.Patient;
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
public class PatientRepository extends RookieRepository {

    public PatientRepository(JdbcTemplate db) {
        super(db);

    }

    public Patient save(Patient patient) {
        Long id = db.queryForObject("select nextval('patient_sequence')", Long.class);
        patient.setId(id);

        db.update("insert into patient (id, name, surname, phone_number, ultima_visita, email) " +
                        "values (?,?,?,?,?,?)", patient.getId(), patient.getName(), patient.getSurname(),
                patient.getPhoneNumber(), Timestamp.from(patient.getUltimaVisita()), patient.getEmail());

        return patient;
    }

    //dati del pomeriggio 13 giugno

    public Optional<Patient> findById(Long id) {
        try {
            Patient patient = db.queryForObject("select * from patient where id = ?", this::map, id);
            return Optional.ofNullable(patient);
        } catch (EmptyResultDataAccessException e) {
        } return Optional.empty();

    }

    private Patient findOriginalPatientById(Long id){
        return db.queryForObject("select * from patient where id = ?", this::map, id);
    }

    private Patient map(ResultSet resultSet, int i) throws SQLException {
        Patient patient = new Patient();
        patient.setId(resultSet.getLong("id"));
        patient.setUltimaVisita(resultSet.getTimestamp("ultima_visita").toInstant());
        patient.setPhoneNumber(resultSet.getLong("phone_number"));
        patient.setName(resultSet.getString("name"));
        patient.setSurname(resultSet.getString("surname"));
        patient.setEmail(resultSet.getString("email"));
        return patient;
    }

    //dati del pomeriggio 14 giugno

    public void delete(Long id){
        int updateCount  = db.update("delete from patient where id= ?", id);
        if (updateCount != 1)
            throw new IllegalStateException(String.format("update count %d, expected 1", id));
    }

    public Page<Patient> findAll(String filter, Pageable pageable){
        StringBuilder queryBuffer = new StringBuilder("select * from patient ");

        List<Object> parameters = new ArrayList<>();
        if(filter != null && !filter.isBlank()){
            queryBuffer.append("where name like ? or surname like ? or email like ?");
            String like = "%" + filter + "%";
            for(int i= 0; i < 3; i++){
                parameters.add(like);
            }
        }
        String query = pagingQuery(queryBuffer, pageable);
        List<Patient> patients = db.query(queryBuffer.toString(), this::map, parameters.toArray());
        return new PageImpl<>(patients, pageable, 0);

    }

}


