package it.intesys.repository;

import it.intesys.domain.Doctor;
import it.intesys.dto.DoctorFilterDTO;
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

public class DoctorRepository extends RookieRepository {

    private final PatientRepository accountRepository;
    public DoctorRepository(JdbcTemplate db, PatientRepository accountRepository){
        super(db);
        this.accountRepository = accountRepository;
    }

    public Doctor save(Doctor doctor) {
        if(doctor.getId() == null){
            Long id = db.queryForObject("select nextval('doctor_sequence') ", Long.class);
            doctor.setId(id);
            db.update("insert into doctor (id, address, avatar, email, name, phone_number, profession, surname) " +
                            "values (?, ?, ?, ?, ?, ?, ?, ?)", doctor.getId(), doctor.getAddress(), doctor.getAvatar(),
                            doctor.getEmail(), doctor.getName(), doctor.getPhoneNumber(),
                            doctor.getProfession(), doctor.getSurname());

            return doctor;
        } else {
            int updateCount = db.update("update doctor set address = ?, avatar = ?, email = ?, name = ?, phone_number = ?, " +
                    "profession = ?, surname = ? where id = ?", doctor.getAddress(), doctor.getAvatar(),
                    doctor.getEmail(),  doctor.getName(), doctor.getPhoneNumber(),
                    doctor.getProfession(), doctor.getSurname(), doctor.getId());
            if(updateCount != 1){
                throw new IllegalStateException(String.format("update count %id, excepted 1", updateCount));
            }
            return findDoctorById(doctor.getId());
        }
    }


    public Optional<Doctor> findById(Long id) {
        try{
            Doctor doctor = findDoctorById(id);
            return Optional.ofNullable(doctor);
        } catch (EmptyResultDataAccessException e){
            logger.warn(e.getMessage());
            return Optional.empty();
        }
    }

    private Doctor findDoctorById(Long id) {
        return db.queryForObject("select * from doctor where id = ?", this::map, id);
    }

    public void deleteDoctor(Long id){
        int updateCount = db.update("delete from doctor where id = ?", id);

        if(updateCount != 1){
            throw new IllegalStateException(String.format("Update count %d, excepted 1", updateCount));
        } else {
            logger.debug("DELETE SUCCESS\nUtente con id = " + id);
        }
    }


    private Doctor map(ResultSet resultSet, int i) throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setId(resultSet.getLong("id"));
        doctor.setName(resultSet.getString("name"));
        doctor.setSurname(resultSet.getString("surname"));
        doctor.setEmail(resultSet.getString("email"));
        doctor.setPhoneNumber(resultSet.getLong("phone_number"));
        doctor.setAddress(resultSet.getString("address"));
        doctor.setAvatar(resultSet.getString("avatar"));
        doctor.setProfession(resultSet.getString("profession")); //mancava
        return doctor;
    }

    public Page<Doctor> findAll(String name, String surname, String profession, Pageable pageable) {
        StringBuilder queryBuffer = new StringBuilder("select * from doctor ");
        List<Object> parameters = new ArrayList<>();
        String whereOrAnd = "where ";
        if (name != null && !name.isBlank()) {
            queryBuffer.append(whereOrAnd).append("name like ?");
            String like = "%" + name + "%";
            parameters.add(like);
            whereOrAnd = "and";
        }

        if (surname != null && !surname.isBlank()) {
            queryBuffer.append(whereOrAnd).append("surname like ?");
            String like = "%" + surname + "%";
            parameters.add(like);
            whereOrAnd = "and";
        }
        if (profession != null && !profession.isBlank()) {
            queryBuffer.append(whereOrAnd).append("profession like ?");
            String like = "%" + profession + "%";
            parameters.add(like);
            whereOrAnd = "and";
        }

        String query = pagingQuery(queryBuffer, pageable);
        List<Doctor> doctors = db.query(query, this::map, parameters.toArray());
        return new PageImpl<>(doctors, pageable, 0);
    }
}