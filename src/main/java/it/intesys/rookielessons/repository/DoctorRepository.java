package it.intesys.rookielessons.repository;

import it.intesys.rookielessons.domain.Doctor;
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
public class DoctorRepository extends HospitalRepository {
    private final it.intesys.rookielessons.repository.PatientRepository patientRepository;
    public DoctorRepository(JdbcTemplate db, it.intesys.rookielessons.repository.PatientRepository patientRepository){
        super(db);
        this.patientRepository = patientRepository;
    }

    public Doctor save(Doctor doctor) {
        if(doctor.getId() == null){
            Long id = db.queryForObject("select nextval('doctor_sequence') ", Long.class);
            doctor.setId(id);
            db.update("insert into doctor (id, name, surname, email, phone_number, address, avatar, profession) " +
                            "values (?, ?, ?, ?, ?, ?, ?, ?)", doctor.getId(), doctor.getName(), doctor.getSurname(), doctor.getEmail(),
                    doctor.getPhoneNumber(), doctor.getAddress(), doctor.getAvatar(), doctor.getProfession());
            return doctor;
        } else {
            int updateCount = db.update("update doctor set name = ?, surname = ?, email = ?, phone_number = ?, address = ?, avatar = ?, profession = ? where id = ?", doctor.getName(), doctor.getSurname(), doctor.getEmail(),
                    doctor.getPhoneNumber(), doctor.getAddress(), doctor.getAvatar(), doctor.getProfession(), doctor.getId());
            if(updateCount != 1){
                throw new IllegalStateException(String.format("Update count %d, excepted 1", updateCount));
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
        doctor.setProfession(resultSet.getString("profession"));
        return doctor;
    }

    public Page<Doctor> findAll(String filter, Pageable pageable) {
        StringBuilder queryBuffer = new StringBuilder("select * from doctor ");
        List<Object> parameters = new ArrayList<>();
        if (filter != null && !filter.isBlank()) {
            queryBuffer.append("where name like ? or surname like ? or email like ? or phone_number like ?");
            String like = "%" + filter + "%";
            for (int i = 0; i < 4; i++) parameters.add(like);
        }
        String query = pagingQuery(queryBuffer, pageable);
        List<Doctor> doctors = db.query(query, this::map, parameters.toArray());
        return new PageImpl<>(doctors, pageable, 0);
    }


}
