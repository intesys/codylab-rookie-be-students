package it.intesys.dto;

import it.intesys.domain.Patient;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {
    public Patient toEntity(PatientDTO patientDTO) {
        Patient patient = new Patient();
        patient.setId(patientDTO.getId());
        patient.setUltimaVisita(patientDTO.getLastAdmission());
        patient.setPhoneNumber(patientDTO.getPhoneNumber());
        patient.setName(patientDTO.getName());
        patient.setSurname(patientDTO.getSurname());
        patient.setEmail(patientDTO.getEmail());
        return patient;
    }

    public PatientDTO toDataTrasferObject(Patient patient) {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(patient.getId());
        patientDTO.setLastAdmission(patient.getUltimaVisita());
        patientDTO.setPhoneNumber(patient.getPhoneNumber());
        patientDTO.setName(patient.getName());
        patientDTO.setSurname(patient.getSurname());
        patientDTO.setEmail(patient.getEmail());
        return patientDTO;

    }
}
