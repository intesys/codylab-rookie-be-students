package it.intesys.rookie.dto;

import it.intesys.rookie.domain.BloodGroup;
import it.intesys.rookie.domain.Patient;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PatientMapper {
    public Patient toEntity(PatientDTO patientDTO) {
        Patient patient = new Patient();
        patient.setId(patientDTO.getId());
        patient.setName(patientDTO.getName());
        patient.setSurname(patientDTO.getSurname());
        patient.setEmail(patientDTO.getEmail());
        patient.setPhoneNumber(patientDTO.getPhoneNumber());
        patient.setLastAdmission(patientDTO.getLastAdmission());
        patient.setAddress(patientDTO.getAddress());
        patient.setAvatar(patientDTO.getAvatar());
        patient.setNotes(patientDTO.getNotes());
        patient.setChronicPatient(patientDTO.isChronicPatient());
        patient.setLastDoctorVisitedId(patientDTO.getLastDoctorVisitedId());
        patient.setOpd(patientDTO.getOpd());
        patient.setIdp(patientDTO.getIdp());
        patient.setBloodGroup(Optional.ofNullable(patientDTO.getBloodGroup())
                .map(s -> BloodGroup.valueOf(s.name()))
                .orElse(null));
        return patient;
    }

    public PatientDTO toDataTransferObject(Patient patient) {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(patient.getId());
        patientDTO.setName(patient.getName());
        patientDTO.setSurname(patient.getSurname());
        patientDTO.setEmail(patient.getEmail());
        patientDTO.setPhoneNumber(patient.getPhoneNumber());
        patientDTO.setLastAdmission(patient.getLastAdmission());
        patientDTO.setAddress(patient.getAddress());
        patientDTO.setAvatar(patient.getAvatar());
        patientDTO.setNotes(patient.getNotes());
        patientDTO.setChronicPatient(patient.getChronicPatient());
        patientDTO.setLastDoctorVisitedId(patient.getLastDoctorVisitedId());
        patientDTO.setBloodGroup(BloodGroupDTO.valueOf(patient.getBloodGroup().name()));
        patientDTO.setOpd(patient.getOpd());
        patientDTO.setIdp(patient.getIdp());
        return patientDTO;

    }
}
