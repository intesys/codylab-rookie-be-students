package it.intesys.rookie.domain;
import it.intesys.rookie.domain.BloodGroup;
import java.time.Instant;
import java.util.List;

public class Patient {

    private Long id;
    private Long opd;
    private String name;
    private String surname;
    private int phone_number;
    private String address;
    private String email;
    private String avatar;

    private BloodGroup bloodGroup;
    private String notes;
    private Boolean chronicPatient;
    private Instant lastAdmission;
    private Long lastDoctorVisitedId;
    private List<PatientRecord> patientRecords;
    private Long[] doctorIds;

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

    public int getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(int phone_number) {
        this.phone_number = phone_number;
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
        return lastAdmission;
    }

    public void setLastAdmission(Instant lastAdmission) {
        this.lastAdmission = lastAdmission;
    }

    public Long getLastDoctorVisitedId() {
        return lastDoctorVisitedId;
    }

    public void setLastDoctorVisitedId(Long lastDoctorVisitedId) {
        this.lastDoctorVisitedId = lastDoctorVisitedId;
    }

    public List<PatientRecord> getPatientRecords() {
        return patientRecords;
    }

    public void setPatientRecords(List<PatientRecord> patientRecords) {
        this.patientRecords = patientRecords;
    }

    public Long[] getDoctorIds() {
        return doctorIds;
    }

    public void setDoctorIds(Long[] doctorIds) {
        this.doctorIds = doctorIds;
    }
    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", opd=" + opd +
                ", idp=" + idp +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phoneNumber=" + phone_number +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", bloodGroup=" + bloodGroup +
                ", notes='" + notes + '\'' +
                ", chronicPatient=" + chronicPatient +
                ", LastAdmission=" + lastAdmission +
                ", lastDoctorVisitedId=" + lastDoctorVisitedId +
                ", patientRecords=" + patientRecords +
                '}';
    }
}