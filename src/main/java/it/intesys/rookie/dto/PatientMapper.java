package it.intesys.rookie.dto;

import it.intesys.rookie.domain.BloodGroup;
import it.intesys.rookie.domain.Patient;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PatientMapper {
    public Patient toEntity(PatientDTO patientDTO){
        Patient patient = new Patient();
        patient.setId(patientDTO.getId());
        patient.setPhoneNumber(patientDTO.getPhoneNumber());
        patient.setOpd(patientDTO.getOpd());
        patient.setIdp(patientDTO.getIdp());
        patient.setLastDoctorVisitedId(patientDTO.getLastDoctorVisitedId());
        patient.setLastAdmission(patientDTO.getLastAdmission());
        patient.setName(patientDTO.getName());
        patient.setSurname(patientDTO.getSurname());
        patient.setEmail(patientDTO.getEmail());
        patient.setAddress(patientDTO.getAddress());
        patient.setAvatar(patientDTO.getAvatar());
        patient.setNotes(patientDTO.getNotes());
        patient.setBloodGroup(Optional.ofNullable(patientDTO.getBloodGroup()).map(b -> BloodGroup.valueOf(b.name())).orElse(null));
        patient.setChronicPatient(patientDTO.getChronicPatient());
        return patient;

    }

    public PatientDTO toDataTransferObject(Patient patient){
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(patient.getId());
        patientDTO.setPhoneNumber(patient.getPhoneNumber());
        patientDTO.setOpd(patient.getOpd());
        patientDTO.setIdp(patient.getIdp());
        patientDTO.setLastDoctorVisitedId(patient.getLastDoctorVisitedId());
        patientDTO.setLastAdmission(patient.getLastAdmission());
        patientDTO.setName(patient.getName());
        patientDTO.setSurname(patient.getSurname());
        patientDTO.setEmail(patient.getEmail());
        patientDTO.setAddress(patient.getAddress());
        patientDTO.setAvatar(patient.getAvatar());
        patientDTO.setNotes(patient.getNotes());
        patientDTO.setBloodGroup(BloodGroupDTO.valueOf(patient.getBloodGroup().name()));
        patientDTO.setChronicPatient(patient.getChronicPatient());
        return patientDTO;

    }
}
