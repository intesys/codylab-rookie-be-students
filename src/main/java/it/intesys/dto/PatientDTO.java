package it.intesys.dto;

import java.time.Instant;

public class PatientDTO {
    private Long id;
    private Instant lastAdmission;
    private String name;
    private String surname;
    private String email;
    private Long phoneNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}