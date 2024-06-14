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

public class PatientRepository {
    private final JdbcTemplate db;

    public PatientRepository(JdbcTemplate db) {
        this.db = db;
    }
    public Patient save(Patient patient) {
        if (patient.getId() == null) {
            Long id = db.queryForObject("select nextval('patient_sequence')", Long.class);
            patient.setId(id);
             db.update("insert into patient (id, name, surname, email, phoneNumber, lastAdmission) " +
                            "values(?, ?, ?, ?, ?, ?)", patient.getId(), patient.getName(), patient.getSurname(), patient.getEmail(),
                    patient.getPhoneNumber(), Timestamp.from(patient.getLastAdmission()));
            return patient;
        } else {
            int updateCount = db.update("update patient set lastAdmission = ?, name = ?, surname = ?, email = ?, phoneNumber = ? where id = ?", Timestamp.from(patient.getLastAdmission()), patient.getName(), patient.getSurname(),
                    patient.getEmail(), patient.getPhoneNumber(), patient.getId());
            if (updateCount != 1)
                throw new IllegalStateException(String.format("Update count %d, expected 1", updateCount));
            return findPatientById(patient.getId());
        }
    }

    public Optional<Patient> findById(Long id) {
        try {
            Patient patient = db.queryForObject("select * from patient where id = ?", this::map, id);
            return Optional.ofNullable(patient);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();

        }
    }
    private Patient findPatientById(Long id) {
        Patient patient = db.queryForObject("select * from patient where id = ?", this::map, id);
        return patient;
    }

    private Patient map(ResultSet resultSet, int i) throws SQLException {
        Patient patient = new Patient();
        patient.setId(resultSet.getLong("id"));
        patient.setName(resultSet.getString("name"));
        patient.setSurname(resultSet.getString("surname"));
        patient.setEmail(resultSet.getString("email"));
        patient.setPhoneNumber(resultSet.getLong("phonenumber"));
        patient.setLastAdmission(resultSet.getTimestamp("lastadmission").toInstant());
        return patient;
    }
    public void delete(Long id) {
        int updateCount = db.update("delete from patient where id = ?", id);
        if (updateCount != 1)
            throw new IllegalStateException(String.format("Update count %d, expected 1", updateCount));
    }

    public Page<Patient> findAll(String filter, Pageable pageable) {
        StringBuilder queryBuffer = new StringBuilder("select * from patient ");
        List<Object> parameters = new ArrayList<>();
        if(filter != null && !filter.isBlank()){
            queryBuffer.append("where name like ? or surname like ? or email like ? or phonenumber like ?");
            String like = "%" + filter + "%";
            for (int i =0; i<4; i++) parameters.add(like);
        }
        String query = pagingQuery(queryBuffer, pageable);
        List<Patient> patients = db.query(query, this::map, parameters.toArray());
        return new PageImpl<>(patients, pageable, 0);
    }
    protected String pagingQuery(StringBuilder query, Pageable pageable) {
        String orderSep = "";
        Sort sort = pageable.getSort();
        if (!sort.isEmpty()) {
            query.append(" order by ");
            for (Sort.Order order: sort) {
                query.append(orderSep)
                        .append(order.getProperty())
                        .append(' ')
                        .append(order.getDirection().isDescending() ? "desc" : "")
                        .append(' ');
                orderSep = ", ";
            }
        }

        query.append("limit ")
                .append(pageable.getPageSize())
                .append(' ')
                .append("offset ")
                .append(pageable.getOffset());

        return query.toString();
    }

}
