package hospital.repository;

import hospital.domain.Patient;
import hospital.domain.PatientRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class PatientRecordRepository extends CommonRepository{
    private final PatientRepository patientRepository;

    public PatientRecordRepository(JdbcTemplate db, PatientRepository patientRepository) {
        super(db);
        this.patientRepository = patientRepository;
    }


    public PatientRecord save (PatientRecord patientRecord) {
        if (patientRecord.getId() == null) {
            Long id = db.queryForObject("select nextval('patient_record_sequence')", Long.class);
            patientRecord.setId(id);
            db.update("Insert into patient_record (id, patientId, doctorId, date, typeVisit, reasonVisit, treatmentMade)" +
                    "values(?, ?, ?, ?, ?, ?, ?)", patientRecord.getId(), patientRecord.getPatientId(), patientRecord.getDoctorId(), Timestamp.from(patientRecord.getDate()), patientRecord.getTypeVisit(), patientRecord.getReasonVisit(), patientRecord.getTreatmentMade());
            Timestamp test = Timestamp.from(patientRecord.getDate());

            Integer count = db.queryForObject("select count(*) from doctor_patient where doctor_id = ? and patient_id = ?", Integer.class, patientRecord.getDoctorId(), patientRecord.getPatientId());
            if (count == null || count == 0) {
                db.update("INSERT INTO doctor_patient (doctor_id, patient_id) values (" + patientRecord.getDoctorId() + ", " + patientRecord.getPatientId() + ")");
            }

            int updateCount = db.update("update patient set lastadmission = ?, lastdoctorvisitedid = ? where id = ?", Timestamp.from(patientRecord.getDate()), patientRecord.getDoctorId(), patientRecord.getPatientId());
            if (updateCount != 1){
                throw new IllegalStateException(String.format("Update count %d, expected 1", updateCount));
            }
            return patientRecord;
        } else {
            int updateCount = db.update("update patient_record set patientId = ?, doctorId = ?, typeVisit = ?, reasonVisit = ?, treatmentMade = ? where id = ?", patientRecord.getPatientId(), patientRecord.getDoctorId(), patientRecord.getTypeVisit(), patientRecord.getReasonVisit(),
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

    private PatientRecord   map(ResultSet resultSet, int i) throws SQLException {
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
    public Page<PatientRecord> findLatestByPatient(Patient patient, int size) {
        StringBuilder queryBuffer = new StringBuilder("select patient_record.* from patient_record" +
                " join patient on patient.id = patient_record.patientid" +
                " where patient_record.patientid = ?");

        List<Object> parameters = List.of(patient.getId());

        PageRequest pageable = PageRequest.of(0, size, Sort.by(Sort.Order.desc("date")));
        String query = pagingQuery(queryBuffer, pageable);

        List<PatientRecord> patients = db.query(query, this::map, parameters.toArray(Object[]::new));
        return new PageImpl<>(patients, pageable, 0);
    }

    public void delete(Long id) {
        int updateCount = db.update("delete from patient_record where id = ?", id);
        if (updateCount != 1){
            throw new IllegalStateException(String.format("Update count %d, expected 1", updateCount));
        }
    }
}
