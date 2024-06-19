package it.intesys.rookielessons.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;

public class DoctorDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id, phoneNumber;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private  String name, surname, email, address, avatar, profession;
    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
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

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                "name" + name +
                "surname" + surname +
                "email" + email +
                "phoneNumber" + phoneNumber +
                "address" + address +
                "avatar" + avatar +
                "profession" + profession +
                '}';
    }
}
