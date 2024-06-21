package it.intesys.rookie.repository;

import it.intesys.rookie.domain.Doctor;
import it.intesys.rookie.domain.Patient;
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
public class PatientRepository extends RookieRepository {

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
            int updateCount = db.update("update patient set opd = ?, idp = ?, name = ?, surname= ?, phoneNumber = ?, address = ?, email = ?, avatar = ?, bloodGroup = ?, notes = ?, chronicPatient = ?, lastDoctorVisitedId = ?, where id = ?", patient.getOpd(), patient.getIdp(), patient.getName(), patient.getSurname(), patient.getPhoneNumber(), patient.getAddress(), patient.getEmail(),
                    patient.getAvatar(), patient.getBloodGroup().ordinal(), patient.getNotes(), patient.getChronicPatient(), patient.getLastDoctorVisitedId(), patient.getId());
            if (updateCount != 1) {
                throw new IllegalStateException(String.format("Update count %d, excepted 1", updateCount));
            }
            return getPatient(patient.getId());
        }
    }


    public Optional<Patient> findById(Long id) {
        try {
            Patient patient = db.queryForObject("select * from patient where id = ?", this::map, id);
            return Optional.ofNullable(patient);
        } catch (EmptyResultDataAccessException e) {
        } return Optional.empty();

    }

    private Patient findOriginalPatientById(Long id){
        return db.queryForObject("select * from patient where id = ?", this::map, id);
    }

    private Patient map(ResultSet resultSet, int i) throws SQLException {
        Patient patient = new Patient();
        patient.setId(resultSet.getLong("id"));
        patient.setUltima_visita(resultSet.getTimestamp("ultima_visita").toInstant());
        patient.setPhone_number(resultSet.getInt("phone_number"));
        patient.setName(resultSet.getString("name"));
        patient.setSurname(resultSet.getString("surname"));
        patient.setEmail(resultSet.getString("email"));
        return patient;
    }

    //dati del pomeriggio 14 giugno

    public void delete(Long id){
        int updateCount  = db.update("delete from patient where id= ?", id);
        if (updateCount != 1)
            throw new IllegalStateException(String.format("update count %d, expected 1", id));
    }

    public Page<Patient> findAll(String filter, Pageable pageable){
        StringBuilder queryBuffer = new StringBuilder("select * from patient");

        List<Object> parameters = new ArrayList<>();
        if(filter != null && !filter.isBlank()){
            queryBuffer.append("where name like ? or surname like ? or email like ?");
            String like = "%" + filter + "%";
            for(int i= 0; i< 4; i++){
                parameters.add(like);
            }
        }
        String query = pagingQuery(queryBuffer, pageable);
        List<Patient> patients = db.query(queryBuffer.toString(), this::map, parameters.toArray());
        return new PageImpl<>(patients, pageable, 0);

    }

}



