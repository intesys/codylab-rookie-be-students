package it.intesys.repository;

import it.intesys.domain.Patient;
import it.intesys.domain.PatientRecord;
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

public class PatientRecordRepository {

    public PatientRecordRepository(JdbcTemplate db) {
        super(db);

    }

    public PatientRecord save(PatientRecord patientRecord) {
        Long id = db.queryForObject("select nextval('patient_sequence')", Long.class);
        patientRecord.setId(id);

        db.update("insert into patient (id, name, surname, email) " +
                        "values (?,?,?,?,?,?)", patientRecord.getId(), patientRecord.getName(), patientRecord.getSurname(),
                patientRecord.getEmail());

        return patientRecord;
    }

    //dati del pomeriggio 13 giugno

    public Optional<PatientRecord> findById(Long id) {
        try {
            PatientRecord patientRecord = db.queryForObject("select * from patient_record where id = ?", this::map, id);
            return Optional.ofNullable(patientRecord);
        } catch (EmptyResultDataAccessException e) {
        } return Optional.empty();

    }

    private PatientRecord findOriginalPatientById(Long id){
        return db.queryForObject("select * from patient_record where id = ?", this::map, id);
    }

    private PatientRecord map(ResultSet resultSet, int i) throws SQLException {
        PatientRecord patientRecord = new PatientRecord();
        patientRecord.setId(resultSet.getLong("id"));
        patientRecord.setName(resultSet.getString("name"));
        patientRecord.setSurname(resultSet.getString("surname"));
        patientRecord.setEmail(resultSet.getString("email"));
        return patientRecord;
    }

    //dati del pomeriggio 14 giugno

    public void delete(Long id){
        int updateCount  = db.update("delete from patient_record where id= ?", id);
        if (updateCount != 1)
            throw new IllegalStateException(String.format("update count %d, expected 1", id));
    }

    public Page<PatientRecord> findAll(String filter, Pageable pageable){
        StringBuilder queryBuffer = new StringBuilder("select * from patient_record");

        List<Object> parameters = new ArrayList<>();
        if(filter != null && !filter.isBlank()){
            queryBuffer.append("where name like ? or surname like ? or email like ?");
            String like = "%" + filter + "%";
            for(int i= 0; i< 4; i++){
                parameters.add(like);
            }
        }
        String query = pagingQuery(queryBuffer, pageable);
        List<Patient> patients = db.query(queryBuffer.toString(), this::map, parameters.toArray());
        return new PageImpl<>(patients, pageable, 0);

    }
}
