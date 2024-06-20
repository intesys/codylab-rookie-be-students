package it.intesys.rookielessons.repository;

import it.intesys.rookielessons.domain.PatientRecord;
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
public class PatientRecordRepository extends HospitalRepository {
    private final PatientRepository patientRepository;
    public PatientRecordRepository(JdbcTemplate db, PatientRepository patientRepository){
        super(db);
        this.patientRepository = patientRepository;
    }

    public PatientRecord save(PatientRecord patientRecord) {
        if(patientRecord.getId() == null){
            Long id = db.queryForObject("select nextval('patient_record_sequence') ", Long.class);
            patientRecord.setId(id);
            db.update("insert into patient_record (id, patient_id, doctor_id, reason_visit, type_visit, treatment_made, date) " +
                            "values (?, ?, ?, ?, ?, ?, ?)", patientRecord.getId(), patientRecord.getPatientId(), patientRecord.getDoctorId(), patientRecord.getReasonVisit(),
                    patientRecord.getTypeVisit(), patientRecord.getTreatmentMade(), Timestamp.from(patientRecord.getDate()));
            return patientRecord;
        } else {
            int updateCount = db.update("update patient_record set patient_id = ?, doctor_id = ?, reason_visit = ?, type_visit = ?, treatment_made = ? where id = ?", patientRecord.getPatientId(), patientRecord.getDoctorId(), patientRecord.getReasonVisit(),
                    patientRecord.getTypeVisit(), patientRecord.getTreatmentMade(), patientRecord.getId());
            if(updateCount != 1){
                throw new IllegalStateException(String.format("Update count %d, excepted 1", updateCount));
            }
            return findPatientRecordById(patientRecord.getId());
        }
    }

    public Optional<PatientRecord> findById(Long id) {
        try{
            PatientRecord patientRecord = findPatientRecordById(id);
            return Optional.ofNullable(patientRecord);
        } catch (EmptyResultDataAccessException e){
            logger.warn(e.getMessage());
            return Optional.empty();
        }
    }

    private PatientRecord findPatientRecordById(Long id) {
        return db.queryForObject("select * from patient_record where id = ?", this::map, id);
    }

    public void deletePatientRecord(Long id){
        int updateCount = db.update("delete from patient_record where id = ?", id);

        if(updateCount != 1){
            throw new IllegalStateException(String.format("Update count %d, excepted 1", updateCount));
        } else {
            logger.debug("DELETE SUCCESS\nUtente con id = " + id);
        }
    }

    private PatientRecord map(ResultSet resultSet, int i) throws SQLException {
        PatientRecord patientRecord = new PatientRecord();
        patientRecord.setId(resultSet.getLong("id"));
        patientRecord.setReasonVisit(resultSet.getString("reason_visit"));
        patientRecord.setTypeVisit(resultSet.getString("type_visit"));
        patientRecord.setTreatmentMade(resultSet.getString("treatment_made"));
        patientRecord.setPatientId(resultSet.getLong("patient_id"));
        patientRecord.setDoctorId(resultSet.getLong("doctor_id"));
        patientRecord.setDate(Optional.ofNullable(resultSet.getTimestamp("date")).map(Timestamp::toInstant).orElse(null));
        return patientRecord;
    }

    public Page<PatientRecord> findAll(String filter, Pageable pageable) {
        StringBuilder queryBuffer = new StringBuilder("select * from patient_record ");
        List<Object> parameters = new ArrayList<>();
        if (filter != null && !filter.isBlank()) {
            queryBuffer.append("where patient_id like ? or doctor_id like ? or reason_visit like ? or type_visit like ? or treatment_made like ?");
            String like = "%" + filter + "%";
            for (int i = 0; i < 4; i++) parameters.add(like);
        }
        String query = pagingQuery(queryBuffer, pageable);
        List<PatientRecord> patientRecords = db.query(query, this::map, parameters.toArray());
        return new PageImpl<>(patientRecords, pageable, 0);
    }


}
