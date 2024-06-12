package it.intesys.rookie.dto;

import it.intesys.rookie.domain.Patient;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {
    public Patient toEntity(PatientDTO patientDTO){
        Patient patient = new Patient();
        patient.setId(patientDTO.getId());
        patient.setPhoneNumber(patientDTO.getPhoneNumber());
        patient.setLastAdmission(patientDTO.getLastAdmission());
        patient.setName(patientDTO.getName());
        patient.setSurname(patientDTO.getSurname());
        patient.setEmail(patientDTO.getEmail());
        return patient;
    }

    public PatientDTO toDataTransferObject(Patient patient){
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(patient.getId());
        patientDTO.setPhoneNumber(patient.getPhoneNumber());
        patientDTO.setLastAdmission(patient.getLastAdmission());
        patientDTO.setName(patient.getName());
        patientDTO.setSurname(patient.getSurname());
        patientDTO.setEmail(patient.getEmail());
        return patientDTO;
    }
}
