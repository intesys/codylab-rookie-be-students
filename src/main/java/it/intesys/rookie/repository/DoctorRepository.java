package it.intesys.rookie.repository;

import it.intesys.rookie.domain.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Repository

public class DoctorRepository extends RookieRepository {

    public DoctorRepository(JdbcTemplate db) {
        super (db);
    }
    public Doctor save(Doctor doctor) {
        Long id = db.queryForObject("select nextval('doctor_sequence')", Long.class);
        doctor.setId(id);
        db.update("insert into doctor (id, name, surname, email, phone_number, profession)"+
                        "values (?, ?, ?, ?, ?, ?)",doctor.getId(), doctor.getName(),doctor.getSurname(),doctor.getEmail(),
                doctor.getPhoneNumber(), doctor.getProfession());
        return doctor;
    }

    public Page<Doctor> findAll(String name, String surname, String profession, Pageable pageable) {
        StringBuilder queryBuffer = new StringBuilder("select * from doctor ");
        List<Object> parameters = new ArrayList<>();
        String whereOrAnd = "where";
        if (name != null) {
            queryBuffer.append(whereOrAnd).append(" name like ? ");
            parameters.add("%" + name + "%");
        }
        if (surname != null) {
            queryBuffer.append(whereOrAnd).append(" surname like ? ");
            parameters.add("%" + surname + "%");
        }
        if (profession != null) {
            queryBuffer.append(whereOrAnd).append(" profession like ? ");
            parameters.add("%" + profession + "%");
        }

        String query = pagingQuery(queryBuffer, pageable);
        List<Doctor> doctors = db.query(query, this::map, parameters.toArray());
        return new PageImpl<>(doctors, pageable, 0);
    }

    private Doctor map(ResultSet resultSet, int i) throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setId(resultSet.getLong("id"));
        doctor.setName(resultSet.getString("name"));
        doctor.setSurname(resultSet.getString("surname"));
        doctor.setEmail(resultSet.getString("email"));
        doctor.setPhoneNumber(resultSet.getInt("phone_number"));
        doctor.setAddress(resultSet.getString("address"));
        doctor.setAvatar(resultSet.getString("avatar"));
        return doctor;
    }
}