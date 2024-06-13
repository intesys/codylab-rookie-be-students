package it.intesys.rookie.repository;

import it.intesys.rookie.domain.Doctor;
import it.intesys.rookie.domain.Patient;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.print.Doc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;


@Repository

public class DoctorRepository {
    private final JdbcTemplate db;

    public DoctorRepository(JdbcTemplate db) {
        this.db = db;
    }
    public Doctor save(Doctor doctor) {
        if (doctor.getId() == null){
            Long id = db.queryForObject("select nextval('doctor_sequence')", Long.class);
            doctor.setId(id);
            db.update("insert into doctor (id, name, surname, email, phoneNumber, profession)"+
                            "values(?, ?, ?, ?, ?, ?)",doctor.getId(), doctor.getName(),doctor.getSurname(),doctor.getEmail(),
                    doctor.getPhoneNumber(), doctor.getProfession());
            return doctor;
        }else{
            db.update ("update doctor set name = ?, surname = ?, email = ?, phoneNumber = ?, profession = ? where id = ?",
                    doctor.getName(), doctor.getSurname(), doctor.getEmail(), doctor.getPhoneNumber(), doctor.getPhoneNumber(), doctor.getId());
            return findAccountById(doctor.getId());
        }

        }

    private Doctor findAccountById(Long id) {
        Doctor doctor = db.queryForObject("select * from doctor where id = ?", this::map, id);
        return doctor;
    }


    public Optional<Doctor> findById(Long id) {
        try {
            Doctor doctor = db.queryForObject("select * from doctor where id = ?", this::map, id);
            return Optional.ofNullable(doctor);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();

        }
    }
    public Optional<Doctor> deleteById(Long id) {
        try {
            Doctor doctor = db.queryForObject("select * from doctor where id = ?", this::map, id);
            db.update("delete from doctor where id = ?",id);
            return Optional.ofNullable(doctor);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();

        }
    }


    private Doctor map(ResultSet resultSet, int i) throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setId(resultSet.getLong("id"));
        doctor.setName(resultSet.getString("name"));
        doctor.setSurname(resultSet.getString("surname"));
        doctor.setEmail(resultSet.getString("email"));
        doctor.setPhoneNumber(resultSet.getLong("phonenumber"));
        doctor.setProfession(resultSet.getString("profession"));
        return doctor;
    }
}
