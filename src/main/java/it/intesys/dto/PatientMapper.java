package it.intesys.dto;

import it.intesys.domain.Patient;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {
    public Patient toEntity(PatientDTO patientDTO) {
        Patient patient = new Patient();
        patient.setId(patientDTO.getId());
        patient.setUltimaVisita(patientDTO.getLastAdmission());
        patient.setName(patientDTO.getName());
        patient.setSurname(patientDTO.getSurname());
        patient.setEmail(patientDTO.getEmail());
        patient.setPhoneNumber(patientDTO.getPhoneNumber());
        patient.setAddress(patientDTO.getAddress());
        patient.setAvatar(patientDTO.getAvatar());
        patient.setBloodGroup(patientDTO.getBloodGroup());
        patient.setNotes(patientDTO.getNotes());
        patient.setChronicPatient(patientDTO.getChronicPatient());
        patient.setOpd(patientDTO.getOpd());
        patient.setIdp(patientDTO.getIdp());
        patient.setLastDoctorVisitedId(patientDTO.getLastDoctorVisitedId());
        patient.setPatientRecords(patientDTO.getPatientRecords());
        patient.setDoctorIds(patientDTO.getDoctorIds());
        return patient;

    }

    public PatientDTO toDataTrasferObject(Patient patient) {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(patient.getId());
        patientDTO.setLastAdmission(patient.getUltimaVisita());;
        patientDTO.setName(patient.getName());
        patientDTO.setSurname(patient.getSurname());
        patientDTO.setEmail(patient.getEmail());
        patientDTO.setPhoneNumber(patient.getPhoneNumber());
        patientDTO.setAddress(patient.getAddress());
        patientDTO.setAvatar(patient.getAvatar());
        patientDTO.setBloodGroup(patient.getBloodGroup());
        patientDTO.setNotes(patient.getNotes());
        patientDTO.setChronicPatient(patient.getChronicPatient());
        patientDTO.setOpd(patient.getOpd());
        patientDTO.setIdp(patient.getIdp());
        patientDTO.setLastDoctorVisitedId(patient.getLastDoctorVisitedId());
        patientDTO.setPatientRecords(patient.getPatientRecords());
        patientDTO.setDoctorIds(patient.getDoctorIds());
        return patientDTO;

    }
}
