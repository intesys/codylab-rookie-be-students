package it.intesys.openhospital.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.intesys.openhospital.domain.BloodGroup;
import it.intesys.openhospital.domain.Patient;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PatientRepository extends CommonRepository {

    public PatientRepository(JdbcTemplate db) {
        super(db);
    }

    public Patient save(Patient patient) {
        if (patient.getId() == null) {
            Long id = db.queryForObject("select nextval('patient_sequence')", Long.class);
            patient.setId(id);
            db.update("Insert into patient (id, opd, idp, name, surname, phoneNumber, address, email, avatar, bloodGroup, notes, chronicPatient, LastAdmission, lastDoctorVisitedId)" +
                            "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", patient.getId(), patient.getOpd(), patient.getIdp(), patient.getName(), patient.getSurname(), patient.getPhoneNumber(), patient.getAddress(), patient.getEmail(),
                    patient.getAvatar(), patient.getBloodGroup().ordinal(), patient.getNotes(), patient.getChronicPatient(), patient.getLastAdmission(), patient.getLastDoctorVisitedId());
            return patient;
        } else {
            int updateCount = db.update("update patient set LastAdmission = ?, opd = ?, idp = ?, name = ?, surname= ?, phoneNumber = ?, address = ?, email = ?, avatar = ?, bloodGroup = ?, notes = ?, chronicPatient = ?,  lastDoctorVisitedId = ?, where id = ?", Timestamp.from(patient.getLastAdmission()), patient.getOpd(), patient.getIdp(), patient.getName(), patient.getSurname(), patient.getPhoneNumber(), patient.getAddress(), patient.getEmail(),
                    patient.getAvatar(), patient.getBloodGroup().ordinal(), patient.getNotes(), patient.getChronicPatient(), patient.getLastDoctorVisitedId(), patient.getId());
            if (updateCount != 1){
                throw new IllegalStateException(String.format("Update count %d, excepted 1", updateCount));
            }
            return getPatient(patient.getId());
        }
    }

    public Optional<Patient> findById(Long id) {
        try {
            Patient patient = getPatient(id);
            return Optional.ofNullable(patient);
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    private Patient getPatient(Long id) {
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
        patient.setPhoneNumber(resultSet.getLong("phonenumber"));
        patient.setAddress(resultSet.getString("address"));
        patient.setEmail(resultSet.getString("email"));
        patient.setAvatar(resultSet.getString("avatar"));
        BloodGroup[] bloodGroups = BloodGroup.values();
        int bloodGroupIndex = resultSet.getInt("bloodgroup");
        patient.setBloodGroup(bloodGroups[bloodGroupIndex]);
        patient.setNotes(resultSet.getString("notes"));
        patient.setChronicPatient(resultSet.getBoolean("chronicpatient"));
        patient.setLastAdmission(Optional.ofNullable(resultSet.getTimestamp("lastadmission")).map(Timestamp::toInstant).orElse(null));
        patient.setLastDoctorVisitedId(resultSet.getLong("lastdoctorvisitedid"));
        return patient;

    }

    public Page<Patient> findAll(String text, Long id, Long opd, Long idp, Long doctorId, Pageable pageable) {
        StringBuilder queryBuffer = new StringBuilder("select * from patient ");
        List<Object> parameters = new ArrayList<>();
        String whereAndOr = "where ";
        if (text != null){
            queryBuffer.append(whereAndOr);
            whereAndOr = "and ";
            queryBuffer.append("name like ? or surname like ? or address like ? or email like ? or avatar like ? or notes like ?");
            for (int i = 0; i < 6; i++) parameters.add("%" + text + "%");
        }

        if (id != null){
            queryBuffer.append(whereAndOr);
            whereAndOr = "and ";
            queryBuffer.append("id = ? ");
            parameters.add(id);
        }

        if (opd != null){
            queryBuffer.append(whereAndOr);
            whereAndOr = "and ";
            queryBuffer.append("opd = ? ");
            parameters.add(opd);
        }

        if (idp != null){
            queryBuffer.append(whereAndOr);
            whereAndOr = "and ";
            queryBuffer.append("idp = ? ");
            parameters.add(idp);
        }

//        if (doctorId != null){
//            queryBuffer.append(whereAndOr);
//            queryBuffer.append("doctorId = ? ");
//            parameters.add(doctorId);
//        }

        String query = pagingQuery(queryBuffer, pageable);
        List<Patient> patients = db.query(query, this::map, parameters.toArray());
        return new PageImpl<>(patients, pageable, 0);
    }


    public void delete(Long id) {
        int updateCount = db.update("delete from patient where id = ?", id);
        if (updateCount != 1){
            throw new IllegalStateException(String.format("Update count %d, expected 1", updateCount));
        }
    }
}

