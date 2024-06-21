package it.intesys.domain;

import java.time.Instant;

public class Patient {
    private Long id;
    private Instant ultimaVisita;
    private String name;
    private String surname;
    private String email;
    private Long phoneNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getUltimaVisita() {
        return ultimaVisita;
    }

    public void setUltimaVisita(Instant ultimaVisita) {
        this.ultimaVisita = ultimaVisita;
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

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", ultima_visita=" + ultimaVisita +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phone_number=" + phoneNumber +
                '}';
    }
}
