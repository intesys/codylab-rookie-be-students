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

import javax.print.Doc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository

public class DoctorRepository {
    public static final int BATCH_SIZE = 100;
    private final JdbcTemplate db;
    private final PatientRepository patientRepository;


    public DoctorRepository(JdbcTemplate db, PatientRepository patientRepository) {
        this.db = db;
        this.patientRepository = patientRepository;
    }
    public Doctor save(Doctor doctor) {
        if (doctor.getId() == null){
            Long id = db.queryForObject("select nextval('doctor_sequence')", Long.class);
            doctor.setId(id);
            db.update("insert into doctor (id, name, surname, email, phoneNumber, profession, avatar, address)"+
                            "values(?, ?, ?, ?, ?, ?, ?, ?)",doctor.getId(), doctor.getName(),doctor.getSurname(),doctor.getEmail(),
                    doctor.getPhoneNumber(), doctor.getProfession(), doctor.getAvatar(), doctor.getAddress());
            return doctor;
        }else{
            int updateCount = db.update ("update doctor set name = ?, surname = ?, email = ?, phoneNumber = ?, profession = ?, avatar = ?, address = ? where id = ?",
                    doctor.getName(), doctor.getSurname(), doctor.getEmail(), doctor.getPhoneNumber(), doctor.getProfession(), doctor.getAvatar(), doctor.getAddress(), doctor.getId());

            return findDoctorById(doctor.getId());
        }
    }
    protected <T> List<T> subtract(List<T> from, List<T> what) {
        ArrayList<T> clone = new ArrayList<>(from);
        clone.removeAll(what);
        return clone;
    }



    public Optional<Doctor> findById(Long id) {
        try {
            Doctor doctor = db.queryForObject("select * from doctor where id = ?", this::map, id);
            return Optional.ofNullable(doctor);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();

        }
    }
    private Doctor findDoctorById(Long id) {
        Doctor doctor = db.queryForObject("select * from doctor where id = ?", this::map, id);
        if(doctor!=null){
            List<Patient> patients = patientRepository.findByDoctorId(id);
            doctor.setPatients(patients);
        }
        return doctor;
    }

    public List<Doctor> findByPatient (Patient patient) {
        return db.query("select a.* from doctor a join doctor_patient b on b.doctor_id = a.id where b.patient_id = ?", this::map, patient.getId());
    }
    public void delete(Long id) {
        int updateCount = db.update("delete from doctor where id = ?", id);
        if (updateCount != 1)
            throw new IllegalStateException(String.format("Update count %d, expected 1", updateCount));
    }


    private Doctor map(ResultSet resultSet, int i) throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setId(resultSet.getLong("id"));
        doctor.setName(resultSet.getString("name"));
        doctor.setSurname(resultSet.getString("surname"));
        doctor.setEmail(resultSet.getString("email"));
        doctor.setPhoneNumber(resultSet.getLong("phonenumber"));
        doctor.setProfession(resultSet.getString("profession"));
        doctor.setAvatar(resultSet.getString("avatar"));
        doctor.setAddress(resultSet.getString("address"));
        return doctor;
    }

    public Page<Doctor> findAll(String name, String surname, String profession, Pageable pageable) {
        StringBuilder queryBuffer = new StringBuilder("select * from doctor ");
        List<Object> parameters = new ArrayList<>();
        String whereAndOr = "where";
        if (name != null){
            queryBuffer.append(whereAndOr);
            whereAndOr = "and";
            queryBuffer.append(" name like ? ");
            parameters.add("%" + name + "%");
        }

        if (surname != null){
            queryBuffer.append(whereAndOr);
            whereAndOr = "and";
            queryBuffer.append(" surname like ? ");
            parameters.add("%" + surname + "%");
        }

        if (profession != null){
            queryBuffer.append(whereAndOr);
            queryBuffer.append(" profession like ? ");
            parameters.add("%" + profession + "%");
        }
        String query = pagingQuery(queryBuffer, pageable);
        List<Doctor> doctors = db.query(query, this::map, parameters.toArray());
        return new PageImpl<>(doctors, pageable, 0);
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
