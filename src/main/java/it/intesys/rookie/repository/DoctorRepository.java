package it.intesys.rookie.repository;

import it.intesys.rookie.domain.Doctor;
import it.intesys.rookie.dto.DoctorDTO;
import it.intesys.rookie.utilities.Utilities;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
            db.update("insert into doctor (id, name, surname, phone_number, address, email, avatar, profession) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?)", doctor.getId(), doctor.getName(), doctor.getSurname(), doctor.getPhoneNumber(), doctor.getAddress(), doctor.getEmail(), doctor.getAvatar(), doctor.getProfession());
            return doctor;
        }  else {
            db.update("update doctor set name = ?, surname = ?, phone_number = ?, address = ?, email = ?, avatar = ?, profession = ? where id = ?", doctor.getName(), doctor.getSurname(), doctor.getPhoneNumber(), doctor.getAddress(), doctor.getEmail(), doctor.getAvatar(), doctor.getProfession(), doctor.getId());
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
        doctor.setName(resultSet.getString("name"));
        doctor.setSurname(resultSet.getString("surname"));
        doctor.setEmail(resultSet.getString("email"));
        doctor.setProfession(resultSet.getString("profession"));
        doctor.setAvatar(resultSet.getString("avatar"));
        doctor.setAddress(resultSet.getString("address"));
        return doctor;
    }

    public Page<Doctor> findAll(String name, String surname, String profession, Pageable pageable) {
        String whereOrAnd = "where ";
        StringBuilder queryBuffer = new StringBuilder("select * from doctor ");
        List<Object> parameters = new ArrayList<>();

        if (name != null && !name.isBlank()) {
            queryBuffer.append(whereOrAnd).append("name like ? ");
            whereOrAnd = "and ";
            parameters.add("%" + name + "%");
        }
        if (surname != null && !surname.isBlank()) {
            queryBuffer.append(whereOrAnd).append("surname like ? ");
            whereOrAnd = "and ";
            parameters.add("%" + surname + "%");
        }
        if (profession != null && !profession.isBlank()) {
            queryBuffer.append(whereOrAnd).append("profession like ?");
            whereOrAnd = "and ";
            parameters.add("%" + profession + "%");
        }

        String query = Utilities.pagingQuery(queryBuffer, pageable);
        List<Doctor> doctors = db.query(query, this::map, parameters.toArray());
        return new PageImpl<>(doctors, pageable, 0);
    }
}
