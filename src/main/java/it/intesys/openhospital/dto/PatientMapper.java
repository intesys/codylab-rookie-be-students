package it.intesys.openhospital.dto;

import it.intesys.openhospital.domain.Patient;
import it.intesys.openhospital.domain.PatientRecord;
import it.intesys.openhospital.repository.PatientRecordRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PatientMapper {
    private final PatientRecordRepository patientRecordRepository;
    private final PatientRecordMapper patientRecordMapper;

    public PatientMapper(PatientRecordRepository patientRecordRepository, PatientRecordMapper patientRecordMapper) {
        this.patientRecordRepository = patientRecordRepository;
        this.patientRecordMapper = patientRecordMapper;
    }

    public Patient toEntity(PatientDTO patientDTO){
        Patient patient = new Patient();
        patient.setId(patientDTO.getId());
        patient.setOpd(patientDTO.getOpd());
        patient.setIdp(patientDTO.getIdp());
        patient.setName(patientDTO.getName());
        patient.setSurname(patientDTO.getSurname());
        patient.setPhoneNumber(patientDTO.getPhoneNumber());
        patient.setAddress(patientDTO.getAddress());
        patient.setEmail(patientDTO.getEmail());
        patient.setAvatar(patientDTO.getAvatar());
        patient.setBloodGroup(patientDTO.getBloodGroup());
        patient.setNotes(patientDTO.getNotes());
        patient.setChronicPatient(patientDTO.getChronicPatient());
        patient.setLastAdmission(patientDTO.getLastAdmission());
        patient.setLastDoctorVisitedId(patientDTO.getLastDoctorVisitedId());
        patient.setDoctorIds(patientDTO.getDoctorIds());
        return patient;
    }

    public PatientDTO toDataTransferObject(Patient patient){
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(patient.getId());
        patientDTO.setOpd(patient.getOpd());
        patientDTO.setIdp(patient.getIdp());
        patientDTO.setName(patient.getName());
        patientDTO.setSurname(patient.getSurname());
        patientDTO.setPhoneNumber(patient.getPhoneNumber());
        patientDTO.setAddress(patient.getAddress());
        patientDTO.setEmail(patient.getEmail());
        patientDTO.setAvatar(patient.getAvatar());
        patientDTO.setBloodGroup(patient.getBloodGroup());
        patientDTO.setNotes(patient.getNotes());
        patientDTO.setChronicPatient(patient.getChronicPatient());
        patientDTO.setLastAdmission(patient.getLastAdmission());
        patientDTO.setLastDoctorVisitedId(patient.getLastDoctorVisitedId());
        patientDTO.setPatientRecords(patientRecordRepository.findLatestByPatient(patient, 5)
                .stream()
                .map(patientRecordMapper::toDataTransferObject).toList());
        patientDTO.setDoctorIds(patient.getDoctorIds());
        return patientDTO;
    }
}
