package it.intesys.openhospital.domain;

import java.time.Instant;
import java.util.Objects;

public class PatientRecord {
    private Long id;
    private Long patientId;
    private Long doctorId;
    private Instant date;
    private String typeVisit;
    private String reasonVisit;
    private String treatmentMade;

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

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
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

    @Override
    public String toString() {
        return "PatientRecord{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", doctorId=" + doctorId +
                ", date=" + date +
                ", typeVisit='" + typeVisit + '\'' +
                ", reasonVisit='" + reasonVisit + '\'' +
                ", treatmentMade='" + treatmentMade + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientRecord that = (PatientRecord) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
