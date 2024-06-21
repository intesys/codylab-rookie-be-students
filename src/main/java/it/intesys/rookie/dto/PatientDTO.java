package it.intesys.rookie.dto;

import it.intesys.rookie.domain.Doctor;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class PatientDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private Long phoneNumber;
    private Instant lastAdmission;
    private String address;
    private String avatar;
    private String notes;
    private boolean chronicPatient;
    private Long lastDoctorVisitedId;
    private BloodGroupDTO bloodGroup;
    private Long opd;
    private Long idp;

    private List<Long> doctorIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Instant getLastAdmission() {
        return lastAdmission;
    }

    public void setLastAdmission(Instant lastAdmission) {
        this.lastAdmission = lastAdmission;
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

    public boolean isChronicPatient() {
        return chronicPatient;
    }

    public void setChronicPatient(boolean chronicPatient) {
        this.chronicPatient = chronicPatient;
    }

    public Long getLastDoctorVisitedId() {
        return lastDoctorVisitedId;
    }

    public void setLastDoctorVisitedId(Long lastDoctorVisitedId) {
        this.lastDoctorVisitedId = lastDoctorVisitedId;
    }

    public BloodGroupDTO getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(BloodGroupDTO bloodGroup) {
        this.bloodGroup = bloodGroup;
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

    public String toString() {
        return "PatientDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", lastAdmission=" + lastAdmission +
                '}';
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
        return Objects.hash(id);
    }

    public List<Long> getDoctorIds() {
        return doctorIds;
    }

    public void setDoctorIds(List<Long> doctorIds) {
        this.doctorIds = doctorIds;
    }
}
