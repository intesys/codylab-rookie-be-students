package it.intesys.openhospital.dto;

import it.intesys.openhospital.domain.BloodGroup;

import java.time.Instant;

public class PatientDTO {
    private Long id;
    private Long opd;
    private Long idp;
    private String name;
    private String surname;
    private Long phoneNumber;
    private String address;
    private String email;
    private String avatar;
    private BloodGroup bloodGroup;
    private String notes;
    private Boolean chronicPatient;
    private Instant LastAdmission;
    private Long lastDoctorVisitedId;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOpd() {
        return opd;
    }

    public void setOpd(Long opd) {
        this.opd = opd;
    }

    public Long getIdp() {
        return idp;
    }

    public void setIdp(Long idp) {
        this.idp = idp;
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

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public BloodGroup getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getChronicPatient() {
        return chronicPatient;
    }

    public void setChronicPatient(Boolean chronicPatient) {
        this.chronicPatient = chronicPatient;
    }

    public Instant getLastAdmission() {
        return LastAdmission;
    }

    public void setLastAdmission(Instant lastAdmission) {
        LastAdmission = lastAdmission;
    }

    public Long getLastDoctorVisitedId() {
        return lastDoctorVisitedId;
    }

    public void setLastDoctorVisitedId(Long lastDoctorVisitedId) {
        this.lastDoctorVisitedId = lastDoctorVisitedId;
    }

}
