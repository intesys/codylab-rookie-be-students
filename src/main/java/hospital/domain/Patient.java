package hospital.domain;

import java.util.List;

public class Patient {
    private Long id;
    private String address;
    private Long idp;
    private Long opd;
    private Long phoneNumber;
    private String notes;
    private boolean chronicPatient;
    private String lastAdmission;
    private String lastDoctorVisitedId;
    private List patientRecords;
    private Long doctorIds;
    private String avatar;

    public Object getChronicPatient() {
        return null;
    }

    private enum bloodGroup{
        ZERO_PLUS,
        ZERO_MINUS,
        A_PLUS,
        A_MINUS,
        B_PLUS,
        B_MINUS,
        AB_PLUS,
        AB_MINUS,
    }
    private String name;
    private String surname;
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getIdp() {
        return this.idp;
    }

    public void setIdp(Long idp) {
        this.idp = idp;
    }

    public Long getOpd() {
        return opd;
    }

    public void setOpd(Long opd) {
        this.opd = opd;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getLastAdmission() {
        return lastAdmission;
    }

    public void setLastAdmission(String lastAdmission) {
        this.lastAdmission = lastAdmission;
    }

    public String getLastDoctorVisitedId() {
        return lastDoctorVisitedId;
    }

    public void setLastDoctorVisitedId(String lastDoctorVisitedId) {
        this.lastDoctorVisitedId = lastDoctorVisitedId;
    }

    public List getPatientRecords() {
        return patientRecords;
    }

    public void setPatientRecords(List patientRecords) {
        this.patientRecords = patientRecords;
    }

    public Long getDoctorIds() {
        return doctorIds;
    }

    public void setDoctorIds(Long doctorIds) {
        this.doctorIds = doctorIds;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", idp=" + idp +
                ", opd=" + opd +
                ", phoneNumber=" + phoneNumber +
                ", notes='" + notes + '\'' +
                ", chronicPatient=" + chronicPatient +
                ", lastAdmission='" + lastAdmission + '\'' +
                ", lastDoctorVisitedId='" + lastDoctorVisitedId + '\'' +
                ", patientRecords=" + patientRecords +
                ", doctorIds=" + doctorIds +
                ", avatar='" + avatar + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
