package it.intesys.rookie.domain;

import java.time.Instant;

public class Patient {
    private Long id, phoneNumber;
    private Instant lastAdmission;
    private String name, surname, email;

    @Override
    public String toString() {
        return "PatientDTO{" +
                "id=" + id +
                ", phoneNumber=" + phoneNumber +
                ", lastAdmission=" + lastAdmission +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
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
}