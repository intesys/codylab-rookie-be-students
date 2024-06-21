package it.intesys.rookie.repository;

import it.intesys.rookie.domain.BloodGroup;
import it.intesys.rookie.domain.Doctor;
import it.intesys.rookie.domain.Patient;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
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

public class PatientRepository extends RookieRepository {
    public static final int BATCH_SIZE = 100;
    private DoctorRepository doctorRepository;
    private final ApplicationContext applicationContext;

    public PatientRepository(JdbcTemplate db, ApplicationContext applicationContext) {
        super(db);
        this.applicationContext = applicationContext;
    }
    public Patient save(Patient patient) {
        if (patient.getId() == null) {
            Long id = db.queryForObject("select nextval('patient_sequence')", Long.class);
            patient.setId(id);
             db.update("insert into patient (id, name, surname, email, phoneNumber, lastAdmission, address, avatar, notes, chronicPatient, lastDoctorVisitedId, bloodGroup, opd, idp) " +
                            "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", patient.getId(), patient.getName(), patient.getSurname(), patient.getEmail(),
                    patient.getPhoneNumber(), Timestamp.from(patient.getLastAdmission()), patient.getAddress(), patient.getAvatar(), patient.getNotes(), patient.getChronicPatient(), patient.getLastDoctorVisitedId(), patient.getBloodGroup().ordinal(), patient.getOpd(), patient.getIdp());

             return patient;
        } else {
            int updateCount = db.update("update patient set lastAdmission = ?, name = ?, surname = ?, email = ?, " +
                            "phoneNumber = ?, address = ?, avatar = ?, notes = ?, chronicPatient = ?, lastDoctorVisitedId = ?, " +
                            "bloodGroup = ?, opd = ?, idp = ? " +
                            "where id = ?", Timestamp.from(patient.getLastAdmission()), patient.getName(), patient.getSurname(),
                    patient.getEmail(), patient.getPhoneNumber(),patient.getAddress(), patient.getAvatar(), patient.getNotes(), patient.getChronicPatient(), patient.getLastDoctorVisitedId(), patient.getBloodGroup().ordinal(), patient.getOpd(), patient.getIdp(), patient.getId());

        }
        List<Doctor> doctors = patient.getDoctors();
        List<Doctor> currentDoctors = findPatientById(patient.getId()).getDoctors();

        List<Doctor> insertions = subtract(doctors, currentDoctors);
        db.batchUpdate("insert into doctor_patient (patient_id, doctor_id) values (?, ?)", insertions, BATCH_SIZE, (ps, doctor) -> {
            ps.setLong(1, doctor.getId());
            ps.setLong(2, patient.getId());
        });

        List<Doctor> deletions = subtract (currentDoctors, doctors);
        db.batchUpdate("delete from doctor_patient where patient_id = ? and doctor_id = ?", deletions, BATCH_SIZE, (ps, doctor) -> {
            ps.setLong(1, doctor.getId());
            ps.setLong(2, patient.getId());
        });
        if(patient.getId()==null){
            return patient;
        }

        return findPatientById(patient.getId());
    }

    public Optional<Patient> findById(Long id) {
        try {
            Patient patient = db.queryForObject("select * from patient where id = ?", this::map, id);
            return Optional.ofNullable(patient);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();

        }
    }
    private Patient findPatientById(Long id) {
        Patient patient = db.queryForObject("select * from patient where id = ?", this::map, id);
        return patient;
    }

    private Patient map(ResultSet resultSet, int i) throws SQLException {
        Patient patient = new Patient();
        patient.setId(resultSet.getLong("id"));
        patient.setOpd(resultSet.getLong("opd"));
        patient.setIdp(resultSet.getLong("idp"));
        patient.setName(resultSet.getString("name"));
        patient.setSurname(resultSet.getString("surname"));
        patient.setEmail(resultSet.getString("email"));
        patient.setPhoneNumber(resultSet.getLong("phonenumber"));
        patient.setAddress(resultSet.getString("address"));
        patient.setAvatar(resultSet.getString("avatar"));
        patient.setNotes(resultSet.getString("notes"));
        patient.setChronicPatient(resultSet.getBoolean("chronicpatient"));
        patient.setLastAdmission(resultSet.getTimestamp("lastadmission").toInstant());
        patient.setLastDoctorVisitedId(resultSet.getLong("lastdoctorvisitedid"));
        BloodGroup[] bloodGroups = BloodGroup.values();
        int bloodGroupsIndex = resultSet.getInt("bloodgroup");
        patient.setBloodGroup(bloodGroups[bloodGroupsIndex]);


        return patient;
    }
    public void delete(Long id) {
        int updateCount = db.update("delete from patient where id = ?", id);
        if (updateCount != 1)
            throw new IllegalStateException(String.format("Update count %d, expected 1", updateCount));
    }

    public Page<Patient> findAll(String text, Long id, Long opd, Long idp, Long doctorId, Pageable pageable) {
        StringBuilder queryBuffer = new StringBuilder("select * from patient ");
        List<Object> parameters = new ArrayList<>();
        String whereAndOr = "where ";
        if (text != null) {
            queryBuffer.append(whereAndOr);
            whereAndOr = "and ";
            queryBuffer.append("name like ? or surname like ? or address like ? or email like ? or avatar like ? or notes like ?");
            for (int i = 0; i < 6; i++) parameters.add("%" + text + "%");
        }

        if (id != null) {
            queryBuffer.append(whereAndOr);
            whereAndOr = "and ";
            queryBuffer.append("id = ? ");
            parameters.add(id);
        }

        if (opd != null) {
            queryBuffer.append(whereAndOr);
            whereAndOr = "and ";
            queryBuffer.append("opd = ? ");
            parameters.add(opd);
        }

        if (idp != null) {
            queryBuffer.append(whereAndOr);
            whereAndOr = "and ";
            queryBuffer.append("idp = ? ");
            parameters.add(idp);
        }
        String query = pagingQuery(queryBuffer, pageable);
        List<Patient> patients = db.query(query, this::map, parameters.toArray());
        return new PageImpl<>(patients, pageable, 0);
    }
    public List<Patient> findByDoctorId(Long doctorId){
        return db.query("select patient.* from doctor_patient " +
                "join patient on doctor_patient.patient_id = patient.id "+
                "where doctor_patient.doctor_id = ?", this::map, doctorId);
    }
    public Page<Patient> findLatestByDoctor(Doctor doctor, int limit){
        StringBuilder queryBuffer = new StringBuilder("select b.* from patientrecord a "+
                "join patient b on b.id = a.patient_id " +
                "where a.doctor_id = ?");
        List<Object> parameters = List.of(doctor.getId());

        PageRequest pageable = PageRequest.of(0,limit,Sort.by(Sort.Order.desc("date")));
        String query = pagingQuery(queryBuffer, pageable);
        List<Patient> patients = db.query(query, this::map, parameters.toArray(Object[]::new));
        return new PageImpl<>(patients, pageable, 0);
    }
    @EventListener(ApplicationReadyEvent.class)
    synchronized void init(){
        doctorRepository = applicationContext.getBean(DoctorRepository.class);
    }

}
