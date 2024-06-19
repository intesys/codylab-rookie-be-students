package it.intesys.openhospital.repository;

import it.intesys.openhospital.domain.Patient;
import it.intesys.openhospital.domain.PatientRecord;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
            return null;
        }
    }
}