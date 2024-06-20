package it.intesys.rookie.repository;

import it.intesys.rookie.domain.PatientRecord;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        if (patientRecord.getId() == null){
            Long id = db.queryForObject("select nextval('patient_record_sequence')", Long.class);
            patientRecord.setId(id);
            db.update("insert into patientrecord (id, patient_id, doctor_id, date, typeVisit, reasonVisit, treatmentMade)"+
                            "values(?, ?, ?, ?, ?, ?, ?)",patientRecord.getId(), patientRecord.getPatientId(),patientRecord.getDoctorId(), Timestamp.from(patientRecord.getDate()),
                    patientRecord.getTypeVisit(), patientRecord.getReasonVisit(), patientRecord.getTreatmentMade());
            db.update("insert into doctor_patient (doctor_id, patient_id)" +
                    "values (?, ?)", patientRecord.getDoctorId(), patientRecord.getPatientId());
            return patientRecord;
        }else{
            int updateCount = db.update ("update patientrecord set date = ?, patient_id = ?, doctor_id = ?, typeVisit = ?, reasonVisit = ?, treatmentMade = ? where id = ?",
                    Timestamp.from(patientRecord.getDate()), patientRecord.getPatientId(), patientRecord.getDoctorId(), patientRecord.getTypeVisit(), patientRecord.getReasonVisit(), patientRecord.getTreatmentMade(), patientRecord.getId());
            if (updateCount != 1)
                throw new IllegalStateException(String.format("Update count %d, expected 1", updateCount));

            return findAccountById(patientRecord.getId());
        }

        }

    private PatientRecord findAccountById(Long id) {
        PatientRecord patientRecord = db.queryForObject("select * from patientrecord where id = ?", this::map, id);
        return patientRecord;
    }


    public Optional<PatientRecord> findById(Long id) {
        try {
            PatientRecord patientRecord = db.queryForObject("select * from patientrecord where id = ?", this::map, id);
            return Optional.ofNullable(patientRecord);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();

        }
    }
    public void delete(Long id) {
        int updateCount = db.update("delete from patientrecord where id = ?", id);
        if (updateCount != 1)
            throw new IllegalStateException(String.format("Update count %d, expected 1", updateCount));
    }


    private PatientRecord map(ResultSet resultSet, int i) throws SQLException {
        PatientRecord patientRecord = new PatientRecord();
        patientRecord.setId(resultSet.getLong("id"));
        patientRecord.setPatientId(resultSet.getLong("patient_id"));
        patientRecord.setDoctorId(resultSet.getLong("doctor_id"));
        patientRecord.setDate(resultSet.getTimestamp("date").toInstant());
        patientRecord.setTypeVisit(resultSet.getString("typevisit"));
        patientRecord.setReasonVisit(resultSet.getString("reasonvisit"));
        patientRecord.setTreatmentMade(resultSet.getString("treatmentmade"));
        return patientRecord;
    }

    public Page<PatientRecord> findAll(String filter, Pageable pageable) {
        StringBuilder queryBuffer = new StringBuilder("select * from patientrecord ");
        List<Object> parameters = new ArrayList<>();
        if(filter != null && !filter.isBlank()){
            queryBuffer.append("where typevisit like ? or reasonvisit like ? or treatmentmade like ?");
            String like = "%" + filter + "%";
            for (int i =0; i<5; i++) parameters.add(like);
        }
        String query = pagingQuery(queryBuffer, pageable);
        List<PatientRecord> patientRecords = db.query(query, this::map, parameters.toArray());
        return new PageImpl<>(patientRecords, pageable, 0);
    }
}
