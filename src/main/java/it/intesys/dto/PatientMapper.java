package it.intesys.dto;

import it.intesys.domain.Patient;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {
    public Patient toEntity(PatientDTO patientDTO) {
        Patient patient = new Patient();
        patient.setId(patientDTO.getId());
        patient.setUltima_visita(patientDTO.getUltima_visita());
        patient.setPhone_number(patientDTO.getPhone_number());
        patient.setName(patientDTO.getName());
        patient.setSurname(patientDTO.getSurname());
        patient.setEmail(patientDTO.getEmail());
        return patient;
    }

    public PatientDTO toDataTrasferObject(Patient patient) {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(patient.getId());
        patientDTO.setUltima_visita(patient.getUltima_visita());
        patientDTO.setPhone_number(patient.getPhone_number());
        patientDTO.setName(patient.getName());
        patientDTO.setSurname(patient.getSurname());
        patientDTO.setEmail(patient.getEmail());
        return patientDTO;

    }
}
