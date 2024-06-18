package it.intesys.dto;

import it.intesys.domain.Patient;
import it.intesys.domain.Status;

import java.time.Instant;
import java.util.List;

public class DoctorDTO {

    private Long id;
    private Instant firstAdmission, lastAdmission; //prima e ultima visita
    private String name, surname, email;
    private int phone_number;
    private Status status;
    private List<Long> patientIds;


    public List<Long> getPatientIds() {
        return patientIds;
    }

    public void setPatientIds(List<Long> patientIds) {
        this.patientIds = patientIds;
    }


    public int getPhone_number() {
        return phone_number;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFirstAdmission() {
        return firstAdmission;
    }

    public void setFirstAdmission(Instant firstAdmission) {
        this.firstAdmission = firstAdmission;
    }

    public Instant getLastAdmission() {
        return lastAdmission;
    }

    public void setLastAdmission(Instant lastAdmission) {
        this.lastAdmission = lastAdmission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", firstAdmission=" + firstAdmission +
                ", lastAdmission=" + lastAdmission +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phone_number=" + phone_number +
                ", status=" + status +
                ", patientIds=" + patientIds +
                '}';
    }
}
