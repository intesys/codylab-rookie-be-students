package it.intesys.domain;

import java.time.Instant;

public class PatientRecord {
    private Long id;
    private Instant ultimaVisita;
    private String name;
    private String surname;
    private String email;
    private int phoneNumber;

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

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "PatientRecord{" +
                "id=" + id +
                ", ultimaVisita=" + ultimaVisita +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber=" + phoneNumber +
                '}';
    }
}