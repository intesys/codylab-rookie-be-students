package it.intesys.rookie.repository;

import it.intesys.rookie.domain.Doctor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public class DoctorRepository {
    private final JdbcTemplate db;

    public DoctorRepository(JdbcTemplate db){
        this.db = db;
    }

    public Doctor save(Doctor doctor) {
        Long id = db.queryForObject("select nextval('doctor_sequence') ", Long.class);
        doctor.setId(id);
        db.update("insert into doctor (id, phone_number, last_admission, name, surname, email, profession) " +
                "values (?, ?, ?, ?, ?, ?, ?)", doctor.getId(), doctor.getPhoneNumber(), Timestamp.from(doctor.getLastAdmission()), doctor.getName(), doctor.getSurname(), doctor.getEmail(), doctor.getProfession());
        return doctor;
    }
}
