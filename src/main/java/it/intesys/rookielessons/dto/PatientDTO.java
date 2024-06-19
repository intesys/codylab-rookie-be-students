package it.intesys.rookielessons.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

public class PatientDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id, phoneNumber;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Instant firstAdmission, lastAdmission;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name, surname, email;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private StatusDTO status;

    @Override
    public String toString() {
        return "PatientDTO{" +
                "id=" + id +
                ", firstAdmission=" + firstAdmission +
                ", lastAdmission=" + lastAdmission +
                ", phoneNumber='" + phoneNumber + '\'' +
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

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public StatusDTO getStatus() {
        return status;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
    }
}
