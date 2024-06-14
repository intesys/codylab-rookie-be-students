package it.intesys.rookie.repository;

import it.intesys.rookie.domain.Doctor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

@Repository
public class DoctorRepository {
    private final JdbcTemplate db;

    public DoctorRepository(JdbcTemplate db){
        this.db = db;
    }

    public Doctor save(Doctor doctor) {
        if(doctor.getId() == null){
            Long id = db.queryForObject("select nextval('doctor_sequence') ", Long.class);
            doctor.setId(id);
            db.update("insert into doctor (id, phone_number, last_admission, name, surname, email, profession) " +
                    "values (?, ?, ?, ?, ?, ?, ?)", doctor.getId(), doctor.getPhoneNumber(), Timestamp.from(doctor.getLastAdmission()), doctor.getName(), doctor.getSurname(), doctor.getEmail(), doctor.getProfession());
            return doctor;
        }  else {
            db.update("update doctor set phone_number = ?, name = ?, surname = ?, email = ?, profession = ? where id = ?", doctor.getPhoneNumber(), doctor.getName(), doctor.getSurname(), doctor.getEmail(), doctor.getProfession(), doctor.getId());
            return findOriginalAccountById(doctor.getId());
         }
    }

    public Optional<Doctor> findById(Long id) {
        try{
            Doctor doctor = db.queryForObject("select * from doctor where id = ?", this::map, id);
            return Optional.ofNullable(doctor);
        } catch (EmptyResultDataAccessException e){
            System.out.println("FOUND ERROR\nDoctor with id = " + id);
            return Optional.empty();
        }
    }

    private Doctor findOriginalAccountById(Long id) {
        Doctor doctor = db.queryForObject("select * from doctor where id = ?", this::map, id);
        return doctor;
    }

    public Optional<Doctor> deleteDoctor(Long id){
        try{
            Doctor doctor = db.queryForObject("select * from doctor where id = ?", this::map, id);
            db.update("delete from doctor where id = ?", id);
            System.out.println("DELETE SUCCESS\nDoctor con id = " + id);
            return Optional.ofNullable(doctor);
        } catch (EmptyResultDataAccessException e){
            System.out.println("DELETE ERROR\nDoctor con id = " + id);
            return Optional.empty();
        }
    }

    private Doctor map(ResultSet resultSet, int i) throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setId(resultSet.getLong("id"));
        doctor.setPhoneNumber(resultSet.getLong("phone_number"));
        doctor.setLastAdmission(resultSet.getTimestamp("last_admission").toInstant());
        doctor.setName(resultSet.getString("name"));
        doctor.setSurname(resultSet.getString("surname"));
        doctor.setEmail(resultSet.getString("email"));
        doctor.setProfession(resultSet.getString("profession"));
        return doctor;
    }
}
