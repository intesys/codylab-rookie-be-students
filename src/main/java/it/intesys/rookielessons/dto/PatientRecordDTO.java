package it.intesys.rookielessons.dto;


import java.time.Instant;

public class PatientRecordDTO {
    private Long id, patientId, doctorId;
    private String typeVisit, reasonVisit, treatmentMade;
    private Instant date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTypeVisit() {
        return typeVisit;
    }

    public void setTypeVisit(String typeVisit) {
        this.typeVisit = typeVisit;
    }

    public String getReasonVisit() {
        return reasonVisit;
    }

    public void setReasonVisit(String reasonVisit) {
        this.reasonVisit = reasonVisit;
    }

    public String getTreatmentMade() {
        return treatmentMade;
    }

    public void setTreatmentMade(String treatmentMade) {
        this.treatmentMade = treatmentMade;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "PatientRecord{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", doctorId=" + doctorId +
                ", typeVisit='" + typeVisit + '\'' +
                ", reasonVisit='" + reasonVisit + '\'' +
                ", treatmentMade='" + treatmentMade + '\'' +
                ", date=" + date +
                '}';
    }
}
