package hospital.repository;

import hospital.domain.Patient;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class PatientRepository extends CommonRepository{
    public PatientRepository(JdbcTemplate db) {
        super(db);
    }

    public Patient save(Patient patient) {
        Long id = db.queryForObject("select nextval(account_sequence) ", Long.class);
        patient.setId(id);
        db.update ("insert into account (id, address, idp, opd, phoneNumber, notes, chronicPatient, lastAdmission, lastDoctorVisitedId, patientRecords, doctorIds, name, surname, email)" +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                patient.getId(),
                patient.getAddress(),
                patient.getIdp(),
                patient.getOpd(),
                patient.getPhoneNumber(),
                patient.getNotes(),
                patient.getChronicPatient(),
                patient.getLastAdmission(),
                patient.getLastDoctorVisitedId(),
                patient.getPatientRecords(),
                patient.getDoctorIds(),
                patient.getAvatar(),
                patient.getName(),
                patient.getSurname(),
                patient.getEmail());

//        List<Patient> patients = doctor.getPatients();
//        List<Patient> currentPatients = getDoctor(doctor.getId()).getPatients();
//
//
//        List<Patient> insertions = subtract(patients, currentPatients);
//        db.batchUpdate("insert into doctor_patient (doctor_id, patient_id) values (?, ?)", insertions, BATCH_SIZE, (ps, patient) -> {
//            ps.setLong(1, doctor.getId());
//            ps.setLong(2, patient.getId());
//        });
//
//        List<Patient> deletions = subtract(currentPatients, patients);
//        db.batchUpdate("delete from doctor_patient where doctor_id = ? and patient_id = ?", deletions, BATCH_SIZE, (ps, account) -> {
//            ps.setLong(1, doctor.getId());
//            ps.setLong(2, account.getId());
//        });
        return patient;
    }

    public Optional<Patient> findById(Long id) {
        try {
            Patient patient = db.queryForObject("select * from patient where id = ?", this::map, id);
            return Optional.ofNullable(patient);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }

    }

    private Patient map (ResultSet resultSet, int i) throws SQLException {
        Patient patient = new Patient();
        patient.setId(resultSet.getLong("id"));
        patient.setAddress(resultSet.getString("address"));
        patient.setIdp(resultSet.getLong("idp"));
        patient.setOpd(resultSet.getLong("odp"));
        patient.setPhoneNumber(resultSet.getLong("phone_number"));
        patient.setNotes(resultSet.getString("notes"));
        patient.setChronicPatient(resultSet.getBoolean("chronic_patient"));
        patient.setLastAdmission(resultSet.getString("last_admission"));
        patient.setLastDoctorVisitedId(resultSet.getString("last_doctor_visited"));
        patient.setPatientRecords((List)resultSet.getObject("patient_records"));
        patient.setDoctorIds(resultSet.getLong("doctors_id"));
        patient.setAvatar(resultSet.getString("avatar"));
        patient.setName(resultSet.getString("name"));
        patient.setSurname(resultSet.getString("surname"));
        patient.setEmail(resultSet.getString("email"));
        return patient;

    }

    public void delete(Long id) {
    }

    public Page<Patient> findAll(String filter, Long id, Long opd, Long idp, Long doctorId, Pageable pageable) {
        return null;
    }
}
