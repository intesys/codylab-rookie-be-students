package it.intesys.rookielessons.dto;
import java.util.Optional;

import it.intesys.rookielessons.domain.Patient;
import it.intesys.rookielessons.domain.Status;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {
    public Patient toEntity(it.intesys.rookielessons.dto.PatientDTO patientDTO){
        Patient patient = new Patient();
        patient.setId(patientDTO.getId());
        patient.setFirstAdmission(patientDTO.getFirstAdmission());
        patient.setLastAdmission(patientDTO.getLastAdmission());
        patient.setPhoneNumber(patientDTO.getPhoneNumber());
        patient.setName(patientDTO.getName());
        patient.setSurname(patientDTO.getSurname());
        patient.setEmail(patientDTO.getEmail());
        patient.setStatus(Optional.ofNullable(patientDTO.getStatus())
                .map(s -> Status.valueOf(s.name()))
                .orElse(null));
        return patient;
    }

    public it.intesys.rookielessons.dto.PatientDTO toDataTransferObject(Patient patient){
        it.intesys.rookielessons.dto.PatientDTO patientDTO = new it.intesys.rookielessons.dto.PatientDTO();
        patientDTO.setId(patient.getId());
        patientDTO.setFirstAdmission(patient.getFirstAdmission());
        patientDTO.setLastAdmission(patient.getLastAdmission());
        patientDTO.setPhoneNumber(patient.getPhoneNumber());
        patientDTO.setName(patient.getName());
        patientDTO.setSurname(patient.getSurname());
        patientDTO.setEmail(patient.getEmail());
        patientDTO.setStatus(StatusDTO.valueOf(patient.getStatus().name()));
        return patientDTO;
    }
}
