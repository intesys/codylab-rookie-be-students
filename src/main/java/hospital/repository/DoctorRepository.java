package hospital.repository;

import hospital.domain.Doctor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;

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
        Long id = db.queryForObject("select nextval(account_sequence) ", Long.class);
        doctor.setId(id);
        db.update ("insert into account (id, last_operation, name, surname, email)" +
                        "values (?, ?, ?, ?, ?, ?, ?)"),
                doctor.getId(), Times tamp.from(doctor.getDataCreated()),
                Timestamp.from(doctor.getDateModified()), doctor.getAlias(),
                doctor.getName(), doctor.getSurname(),
                doctor.getEmail());
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

    private Doctor map (ResultSet resultSet, int i) throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setId(resultSet.getLong("id"));
        doctor.setLastOperation(resultSet.getTimestamp("last_operation").toInstant());
        doctor.setLastVisit(resultSet.getString("last_visit"));
        doctor.setName(resultSet.getString("name"));
        doctor.setSurname(resultSet.getString("surname"));
        doctor.setEmail(resultSet.getString("email"));
        return doctor;

    }
}
    