package it.intesys.rookie.repository;

import it.intesys.rookie.domain.Patient;
import it.intesys.rookie.domain.PatientRecord;
import it.intesys.rookie.dto.PatientRecordDTO;
import it.intesys.rookie.utilities.Utilities;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.*;
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

    private final PatientRepository patientRepository;

    public PatientRecordRepository(JdbcTemplate db, PatientRepository patientRepository) {
        super(db);
        this.patientRepository = patientRepository;
    }


    public PatientRecord save(PatientRecord patientRecord) {
        if(patientRecord.getId() == null){ //SE NON ESISTE
            Long id = db.queryForObject("select nextval('patient_record_sequence') ", Long.class);
            patientRecord.setId(id);

            db.update("insert into patient_record (id, patient_id, doctor_id, date, type_visit, reason_visit, treatment_made) " +
                    "values (?, ?, ?, ?, ?, ?, ?)", patientRecord.getId(), patientRecord.getPatientId(), patientRecord.getDoctorId(), Timestamp.from(patientRecord.getDate()),
                    patientRecord.getTypeVisit(), patientRecord.getReasonVisit(), patientRecord.getTreatmentMade());

            Integer count = db.queryForObject("select count(*) from doctor_patient where doctor_id = ? and patient_id = ?", Integer.class, patientRecord.getDoctorId(), patientRecord.getPatientId());
            if (count == null || count == 0)
                db.update("INSERT INTO doctor_patient (doctor_id, patient_id) values (" + patientRecord.getDoctorId() + ", " + patientRecord.getPatientId() + ")");

            Integer countPatient = db.queryForObject("select count(*) from patient where id = ? ", Integer.class, patientRecord.getPatientId());
            if (countPatient != null)
                db.update("update patient set last_doctor_visited_id = ? where id = ?", patientRecord.getDoctorId(), patientRecord.getPatientId());


            return patientRecord;
        }  else {
            db.update("update patient_record set patient_id = ?, doctor_id = ?, type_visit = ?, reason_visit = ?, treatment_made = ? where id = ?",
                    patientRecord.getPatientId(), patientRecord.getDoctorId(), patientRecord.getTypeVisit(), patientRecord.getReasonVisit(), patientRecord.getTreatmentMade(), patientRecord.getId());
            return findOriginalAccountById(patientRecord.getId());
        }
    }

    public Optional<PatientRecord> findById(Long id) {
        try{
            PatientRecord patientRecord = db.queryForObject("select * from patient_record where id = ?", this::map, id);
            return Optional.ofNullable(patientRecord);
        } catch (EmptyResultDataAccessException e){
            System.out.println("FOUND ERROR\nPatientRecord with id = " + id);
            return Optional.empty();
        }
    }

    private PatientRecord findOriginalAccountById(Long id) {
        PatientRecord patientRecord = db.queryForObject("select * from patient_record where id = ?", this::map, id);
        return patientRecord;
    }

    public Optional<PatientRecord> deletePatientRecord(Long id) {
        try{
            PatientRecord patientRecord = db.queryForObject("select * from patient_record where id = ?", this::map, id);
            db.update("delete from patient_record where id = ?", id);
            System.out.println("DELETE SUCCESS\nPatientRecord con id = " + id);
            return Optional.ofNullable(patientRecord);
        } catch (EmptyResultDataAccessException e){
            System.out.println("DELETE ERROR\nPatientRecord con id = " + id);
            return Optional.empty();
        }
    }

    private PatientRecord map(ResultSet resultSet, int i) throws SQLException {
        PatientRecord patientRecord = new PatientRecord();
        patientRecord.setId(resultSet.getLong("id"));
        patientRecord.setPatientId(resultSet.getLong("patient_id"));
        patientRecord.setDoctorId(resultSet.getLong("doctor_id"));
        patientRecord.setDate(Optional.ofNullable(resultSet.getTimestamp("date")).map(Timestamp::toInstant).orElse(null));
        patientRecord.setTypeVisit(resultSet.getString("type_visit"));
        patientRecord.setReasonVisit(resultSet.getString("reason_visit"));
        patientRecord.setTreatmentMade(resultSet.getString("treatment_made"));
        return patientRecord;
    }

    public Page<PatientRecord> findLatestByPatient(Patient patient, int size) {
        StringBuilder queryBuffer = new StringBuilder("select patient_record.* from patient_record" +
                " join patient on patient.id = patient_record.patient_id" +
                " where patient_record.patient_id = ?");

        List<Object> parameters = List.of(patient.getId());

        PageRequest pageable = PageRequest.of(0, 1, Sort.by(Sort.Order.desc("date")));
        String query = pagingQuery(queryBuffer, pageable);

        List<PatientRecord> patients = db.query(query, this::map, parameters.toArray(Object[]::new));
        return new PageImpl<>(patients, pageable, 0);
    }
}
