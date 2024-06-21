package it.intesys.rookie.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Patient {
    private Long id, phoneNumber, opd, idp, lastDoctorVisitedId;
    private Instant lastAdmission;
    private String name, surname, email, address, avatar, notes;
    private Boolean chronicPatient;
    private BloodGroup bloodGroup;
    private List<PatientRecord> patientRecords;

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", phoneNumber=" + phoneNumber +
                ", opd=" + opd +
                ", idp=" + idp +
                ", lastDoctorVisitedId=" + lastDoctorVisitedId +
                ", lastAdmission=" + lastAdmission +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", avatar='" + avatar + '\'' +
                ", notes='" + notes + '\'' +
                ", chronicPatient=" + chronicPatient +
                ", bloodGroup=" + bloodGroup +
                ", patientRecords=" + patientRecords +
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

    public BloodGroup getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public List<PatientRecord> getPatientRecords() {
        return patientRecords;
    }

    public void setPatientRecords(List<PatientRecord> patientRecords) {
        this.patientRecords = patientRecords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient that = (Patient) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
