package it.intesys.rookie.dto;

public class PatientFilterDTO {

    private String text;
    private Long opd;
    private Long idp;
    private Long doctorId;
    private Long id;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
