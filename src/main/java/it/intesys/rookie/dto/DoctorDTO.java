package it.intesys.rookie.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Objects;

public class DoctorDTO {
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name, surname, email, profession, avatar, address, phoneNumber;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<PatientDTO> latestPatients;

    @Override
    public String toString() {
        return "DoctorDTO{" +
                "id=" + id +
                ", phoneNumber=" + phoneNumber +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", profession='" + profession + '\'' +
                ", avatar='" + avatar + '\'' +
                ", address='" + address + '\'' +
                ", patientIds=" + latestPatients +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<PatientDTO> getLatestPatients() {
        return latestPatients;
    }

    public void setLatestPatients(List<PatientDTO> patientIds) {
        this.latestPatients = patientIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoctorDTO doctorDTO = (DoctorDTO) o;
        return Objects.equals(id, doctorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
