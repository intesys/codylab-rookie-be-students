package it.intesys.rookie.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PatientDTO {
    private Long id, phoneNumber, opd, idp;
    private Instant lastAdmission;
    private String name, surname, email, address, avatar, notes;
    private Boolean chronicPatient;
    private BloodGroupDTO bloodGroup;
    private List<Long> doctorIds;
    private List<PatientRecordDTO> patientRecords;
    private Long lastDoctorVisitedId;

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
                ", doctorIds=" + doctorIds +
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

    public BloodGroupDTO getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(BloodGroupDTO bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public List<PatientRecordDTO> getPatientRecords() {
        return patientRecords;
    }

    public void setPatientRecords(List<PatientRecordDTO> patientRecords) {
        this.patientRecords = patientRecords;
    }

    public List<Long> getDoctorIds() {
        if(doctorIds == null){
            doctorIds = new ArrayList<>();
        }
        return doctorIds;
    }

    public void setDoctorIds(List<Long> doctorIds) {
        this.doctorIds = doctorIds;
    }

    public Long getLastDoctorVisitedId() {
        return lastDoctorVisitedId;
    }

    public void setLastDoctorVisitedId(Long lastDoctorVisitedId) {
        this.lastDoctorVisitedId = lastDoctorVisitedId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientDTO that = (PatientDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
