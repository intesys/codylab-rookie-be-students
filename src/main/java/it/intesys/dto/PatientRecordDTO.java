package it.intesys.dto;

import java.time.Instant;

public class PatientRecordDTO {


    private Long id;
    private Instant Date;
    private Long patientId, doctorId;
    private String reasonVisit, typeVisit, TreatmentMade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return Date;
    }

    public void setDate(Instant date) {
        Date = date;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getReasonVisit() {
        return reasonVisit;
    }

    public void setReasonVisit(String reasonVisit) {
        this.reasonVisit = reasonVisit;
    }

    public String getTypeVisit() {
        return typeVisit;
    }

    public void setTypeVisit(String typeVisit) {
        this.typeVisit = typeVisit;
    }

    public String getTreatmentMade() {
        return TreatmentMade;
    }

    public void setTreatmentMade(String treatmentMade) {
        TreatmentMade = treatmentMade;
    }

    @Override
    public String toString() {
        return "PatientRecordDTO{" +
                "id=" + id +
                ", Date=" + Date +
                ", patientId=" + patientId +
                ", doctorId=" + doctorId +
                ", reasonVisit='" + reasonVisit + '\'' +
                ", typeVisit='" + typeVisit + '\'' +
                ", TreatmentMade='" + TreatmentMade + '\'' +
                '}';
    }
}