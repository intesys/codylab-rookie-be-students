package it.intesys.dto;

import it.intesys.domain.PatientRecord;

import java.time.Instant;
import java.util.List;

public class PatientDTO {
    private Long id;
    private Instant lastAdmission;
    private String name;
    private String surname;
    private String email;
    private Long phoneNumber;
    private String address;
    private String avatar;
    private String bloodGroup;
    private String notes;
    private Boolean chronicPatient;
    private Long opd;
    private Long idp;
    private Long lastDoctorVisitedId;
    private List<PatientRecord> PatientRecords;
    private Long[] doctorIds;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
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

    public Long getLastDoctorVisitedId() {
        return lastDoctorVisitedId;
    }

    public void setLastDoctorVisitedId(Long lastDoctorVisitedId) {
        this.lastDoctorVisitedId = lastDoctorVisitedId;
    }

    public List<PatientRecord> getPatientRecords() {
        return PatientRecords;
    }

    public void setPatientRecords(List<PatientRecord> patientRecords) {
        PatientRecords = patientRecords;
    }

    public Long[] getDoctorIds() {
        return doctorIds;
    }

    public void setDoctorIds(Long[] doctorIds) {
        this.doctorIds = doctorIds;
    }
}
