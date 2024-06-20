package it.intesys.rookie.repository;

import it.intesys.rookie.domain.BloodGroup;
import it.intesys.rookie.domain.Doctor;
import it.intesys.rookie.domain.Patient;
import it.intesys.rookie.utilities.Utilities;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PatientRepository extends Utilities{
    private final JdbcTemplate db;

    public PatientRepository(JdbcTemplate db) throws SQLException {
        this.db = db;
    }

    public Patient save(Patient patient) {
        if(patient.getId() == null){ //SE NON ESISTE
            Long id = db.queryForObject("select nextval('patient_sequence') ", Long.class);
            patient.setId(id);
            db.update("insert into patient (id, opd, idp, name, surname, phone_number, address, email, avatar, " +
                    "blood_group, notes, chronicpatient, last_admission) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    patient.getId(), patient.getOpd(), patient.getIdp(), patient.getName(), patient.getSurname(),
                    patient.getPhoneNumber(), patient.getAddress(), patient.getEmail(), patient.getAvatar(), patient.getBloodGroup().ordinal(),
                    patient.getNotes(), patient.getChronicPatient(), Timestamp.from(patient.getLastAdmission()));

            return patient;
        }  else {
            db.update("update patient set odp = ?, idp = ?, name = ?, surname = ?, phone_number = ?, address = ?, email = ?, avatar = ?, blood_group = ?, notes = ?, chronicpatient = ? where id = ?",
                    patient.getOpd(), patient.getIdp(), patient.getName(), patient.getSurname(), patient.getPhoneNumber(), patient.getAddress(), patient.getEmail(), patient.getAvatar(), patient.getBloodGroup(), patient.getNotes(), patient.getChronicPatient(), patient.getId());
            return findOriginalAccountById(patient.getId());
        }
    }

    public Optional<Patient> findById(Long id) {
        try{
            Patient patient = db.queryForObject("select * from patient where id = ?", this::map, id);
            return Optional.ofNullable(patient);
        } catch (EmptyResultDataAccessException e){
            System.out.println("FOUND ERROR\nPatient with id = " + id);
            return Optional.empty();
        }
    }

    private Patient findOriginalAccountById(Long id) {
        Patient patient = db.queryForObject("select * from patient where id = ?", this::map, id);
        return patient;
    }

    public Optional<Patient> deletePatient(Long id) {
        try{
            Patient patient = db.queryForObject("select * from patient where id = ?", this::map, id);
            db.update("delete from patient where id = ?", id);
            System.out.println("DELETE SUCCESS\nPatient con id = " + id);
            return Optional.ofNullable(patient);
        } catch (EmptyResultDataAccessException e){
            System.out.println("DELETE ERROR\nPatient con id = " + id);
            return Optional.empty();
        }
    }

    private Patient map(ResultSet resultSet, int i) throws SQLException {
        Patient patient = new Patient();
        patient.setId(resultSet.getLong("id"));
        patient.setPhoneNumber(resultSet.getLong("phone_number"));
        patient.setOpd(resultSet.getLong("opd"));
        patient.setIdp(resultSet.getLong("idp"));
        patient.setLastAdmission(Optional.ofNullable(resultSet.getTimestamp("last_admission")).map(Timestamp::toInstant).orElse(null));
        patient.setName(resultSet.getString("name"));
        patient.setSurname(resultSet.getString("surname"));
        patient.setEmail(resultSet.getString("email"));
        patient.setAddress(resultSet.getString("address"));
        patient.setAvatar(resultSet.getString("avatar"));
        patient.setNotes(resultSet.getString("notes"));
        patient.setChronicPatient(resultSet.getBoolean("chronicpatient"));
        patient.setBloodGroup(BloodGroup.values()[resultSet.getInt("blood_group")]);
        patient.setLastDoctorVisitedId(resultSet.getLong("last_doctor_visited_id"));
        return patient;
    }

    public Page<Patient> findAll(Long id, Long opd, Long idp, Long lastDoctorVisitedId, String text, Pageable pageable) {
        String whereOrAnd = "where ";
        StringBuilder queryBuffer = new StringBuilder("select * from patient ");
        List<Object> parameters = new ArrayList<>();


        if (lastDoctorVisitedId != null) {
            queryBuffer.append(whereOrAnd).append("last_doctor_visited_id = ? ");
            whereOrAnd = "and ";
            parameters.add(lastDoctorVisitedId);
        }

        if (id != null) {
            queryBuffer.append(whereOrAnd).append("id = ? ");
            whereOrAnd = "and ";
            parameters.add(id);
        }
        if (idp != null) {
            queryBuffer.append(whereOrAnd).append("idp = ? ");
            whereOrAnd = "and ";
            parameters.add(idp);
        }
        if (opd != null) {
            queryBuffer.append(whereOrAnd).append("opd = ? ");
            whereOrAnd = "and ";
            parameters.add(opd);
        }

        if (text != null && !text.isBlank()) {
            queryBuffer.append(whereOrAnd).append("name like ? or surname like ? or address like ? or email like ? or notes like ?");
            for(int i = 0; i<5; i++){
                parameters.add(text);
            }
        }

        String query = Utilities.pagingQuery(queryBuffer, pageable);
        List<Patient> patient = db.query(query, this::map, parameters.toArray());
        return new PageImpl<>(patient, pageable, 0);
    }

    public List<Patient> findByDoctorId(Long doctorId) {
        return db.query("select patient.* from doctor_patient " +
                "join patient on doctor_patient.patient_id = patient.id " +
                "where doctor_patient.doctor_id = ?", this::map, doctorId);
    }

    public Page<Patient> findLatestByDoctor(Doctor doctor, int size){
        StringBuilder queryBuffer = new StringBuilder("select patient.* from patient_record" +
                " join patient on patient.id = patient_record.patient_id" +
                " where patient_record.doctor_id = ?");

        List<Object> parameters = List.of(doctor.getId());

        PageRequest pageable = PageRequest.of(0, size, Sort.by(Sort.Order.desc("date")));
        String query = pagingQuery(queryBuffer, pageable);

        List<Patient> patients = db.query(query, this::map, parameters.toArray(Object[]::new));
        return new PageImpl<>(patients, pageable, 0);
    }
}
