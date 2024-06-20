package it.intesys.rookie.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class DoctorDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name, surname, email, avatar, profession, address;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String phoneNumber;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<PatientDTO> latestPatients;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<PatientDTO> getLatestPatients() {
        return latestPatients;
    }

    public void setLatestPatients(List<PatientDTO> latestPatients) {
        this.latestPatients = latestPatients;
    }

    @Override
    public String toString() {
        return "DoctorDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", profession='" + profession + '\'' +
                ", address='" + address + '\'' +
                ", phone_number=" + phoneNumber +
                ", latestPatients=" + latestPatients +
                '}';
    }
}

