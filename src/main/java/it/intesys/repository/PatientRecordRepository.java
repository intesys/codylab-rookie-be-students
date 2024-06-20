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

public class PatientRecordRepository extends RookieRepository {

    public PatientRecordRepository(JdbcTemplate db) {
        super(db);

    }

    public PatientRecord save(PatientRecord patientRecord) {
        Long id = db.queryForObject("select nextval('patientRecord_sequence')", Long.class);
        patientRecord.setId(id);

        db.update("insert into patientRecord (id, date, patientId, doctorId, reasonVisit, typeVisit, treatmentMade) " +
                        "values (?,?,?,?,?,?, ?)", patientRecord.getId(), patientRecord.getDate(), patientRecord.getPatientId(), patientRecord.getDoctorId(),
        patientRecord.getReasonVisit(), patientRecord.getTypeVisit(), patientRecord.getTreatmentMade());
        return patientRecord;
    }

    //dati del pomeriggio 13 giugno

    public Optional<PatientRecord> findById(Long id) {
        try {
            PatientRecord patientRecord = db.queryForObject("select * from patientRecord where id = ?", this::map, id);
            return Optional.ofNullable(patientRecord);
        } catch (EmptyResultDataAccessException e) {
        } return Optional.empty();

    }

    private PatientRecord findOriginalPatientById(Long id){
        return db.queryForObject("select * from patientRecord where id = ?", this::map, id);
    }

    private PatientRecord map(ResultSet resultSet, int i) throws SQLException {
        PatientRecord patientRecord = new PatientRecord();
        patientRecord.setId(resultSet.getLong("id"));
        patientRecord.setReasonVisit(resultSet.getString("reasonVisit"));
        patientRecord.setTypeVisit(resultSet.getString("typeVisit"));
        patientRecord.setTreatmentMade(resultSet.getString("treatmentMade"));
        patientRecord.setDate(Optional.ofNullable(resultSet.getTimestamp("date")).map(Timestamp::toInstant).orElse(null));
        patientRecord.setPatientId(resultSet.getLong("patientId"));
        patientRecord.setDoctorId(resultSet.getLong("doctorId"));
        return patientRecord;
    }

    //dati del pomeriggio 14 giugno

    public void delete(Long id){
        int updateCount  = db.update("delete from patientRecord where id= ?", id);
        if (updateCount != 1)
            throw new IllegalStateException(String.format("update count %d, expected 1", id));
    }

    public Page<PatientRecord> findAll(String filter, Pageable pageable){
        StringBuilder queryBuffer = new StringBuilder("select * from patientRecord");

        List<Object> parameters = new ArrayList<>();
        if(filter != null && !filter.isBlank()){
            queryBuffer.append("where name like ? or surname like ? or email like ?");
            String like = "%" + filter + "%";
            for(int i= 0; i< 4; i++){
                parameters.add(like);
            }
        }
        String query = pagingQuery(queryBuffer, pageable);
        List<PatientRecord> patientsRecord = db.query(queryBuffer.toString(), this::map, parameters.toArray());
        return new PageImpl<>(patientsRecord, pageable, 0);

    }
}
