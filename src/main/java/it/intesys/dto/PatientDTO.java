package it.intesys.dto;

import java.time.Instant;

public class PatientDTO {
    private Long id;
    private Instant ultima_visita;
    private String name;
    private String surname;
    private String email;
    private int phone_number;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getUltima_visita() {
        return ultima_visita;
    }

    public void setUltima_visita(Instant ultima_visita) {
        this.ultima_visita = ultima_visita;
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

    public int getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(int phone_number) {
        this.phone_number = phone_number;
    }
}
