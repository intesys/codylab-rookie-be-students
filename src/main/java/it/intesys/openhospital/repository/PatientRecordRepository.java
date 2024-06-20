package it.intesys.openhospital.repository;

import it.intesys.openhospital.domain.Patient;
import it.intesys.openhospital.domain.PatientRecord;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

@Repository
public class PatientRecordRepository extends CommonRepository {

    public PatientRecordRepository(JdbcTemplate db) {
        super(db);
    }

    public PatientRecord save(PatientRecord patientRecord) {
        if (patientRecord.getId() == null) {
            Long id = db.queryForObject("select nextval('patient_record_sequence')", Long.class);
            patientRecord.setId(id);
            db.update("Insert into patient_record (id, patientId, doctorId, date, typeVisit, reasonVisit, treatmentMade)" +
                    "values(?, ?, ?, ?, ?, ?, ?)", patientRecord.getId(), patientRecord.getPatientId(), patientRecord.getDoctorId(),Timestamp.from(patientRecord.getDate()), patientRecord.getTypeVisit(), patientRecord.getReasonVisit(), patientRecord.getTreatmentMade());
            Timestamp test = Timestamp.from(patientRecord.getDate());

            int updateCount = db.update("update patient set lastadmission = ?, lastdoctorvisitedid = ? where id = ?", Timestamp.from(patientRecord.getDate()), patientRecord.getDoctorId(), patientRecord.getPatientId());
            if (updateCount != 1){
                throw new IllegalStateException(String.format("Update count %d, expected 1", updateCount));
            }
            return patientRecord;
        } else {
            int updateCount = db.update("update patient_record set patientId = ?, doctorId = ?, date = ?, typeVisit = ?, reasonVisit = ?, treatmentMade = ? where id = ?", patientRecord.getPatientId(), patientRecord.getDoctorId(), patientRecord.getDate(), patientRecord.getTypeVisit(), patientRecord.getReasonVisit(),
                    patientRecord.getTreatmentMade(), patientRecord.getId());
            if (updateCount != 1){
                throw new IllegalStateException(String.format("Update count %d, expected 1", updateCount));
            }
            return getPatientRecord(patientRecord.getId());
        }
    }

    public Optional<PatientRecord> findById(Long id) {
            PatientRecord patientRecord = getPatientRecord(id);
            return Optional.ofNullable(patientRecord);
    }

    private PatientRecord getPatientRecord(Long id){
        return db.queryForObject("select * from patient_record where id = ?", this::map, id);
    }

    private PatientRecord map(ResultSet resultSet, int i) throws SQLException {
        PatientRecord patientRecord = new PatientRecord();
        patientRecord.setId(resultSet.getLong("id"));
        patientRecord.setPatientId(resultSet.getLong("patientid"));
        patientRecord.setDoctorId(resultSet.getLong("doctorid"));
        patientRecord.setDate(Optional.ofNullable(resultSet.getTimestamp("date")).map(Timestamp::toInstant).orElse(null));
        patientRecord.setTypeVisit(resultSet.getString("typevisit"));
        patientRecord.setReasonVisit(resultSet.getString("reasonvisit"));
        patientRecord.setTreatmentMade(resultSet.getString("treatmentmade"));
        return patientRecord;
    }

    public void delete(Long id) {
        int updateCount = db.update("delete from patient_record where id = ?", id);
        if (updateCount != 1){
            throw new IllegalStateException(String.format("Update count %d, expected 1", updateCount));
        }
    }
}