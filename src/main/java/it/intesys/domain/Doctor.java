package it.intesys.domain;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class Doctor {

    private Long id;
    private Instant firstAdmission, lastAdmission; //prima e ultima visita
    private String name, surname, email;
    private int phone_number;
    private Status status;
    private List<Patient> patients;


    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
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
                ", patients=" + patients +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(id, doctor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
