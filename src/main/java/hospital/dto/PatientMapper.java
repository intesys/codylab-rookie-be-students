package hospital.dto;

import hospital.domain.Patient;
import org.springframework.stereotype.Component;
import java.util.List;


@Component
public class PatientMapper {
    public Patient toEntity(PatientDTO patientDTO){
        Patient patient = new Patient();
        patient.setId(patientDTO.getId());
        patient.setName(patientDTO.getName());
        patient.setSurname(patientDTO.getSurname());
        patient.setEmail(patientDTO.getEmail());
        patient.setId(patientDTO.getId());
        patient.setAddress(patientDTO.getAddress());
        patient.setIdp(patientDTO.getIdp());
        patient.setOpd(patientDTO.getOpd());
        patient.setPhoneNumber(patientDTO.getPhoneNumber());
        patient.setNotes(patientDTO.getNotes());
        patient.setChronicPatient(patientDTO.isChronicPatient());
        patient.setLastAdmission(patientDTO.getLastAdmission());
        patient.setLastDoctorVisitedId(patientDTO.getLastDoctorVisitedId());
        patient.setPatientRecords((List)patientDTO.getPatientRecords());
        patient.setDoctorIds(patientDTO.getDoctorIds());
        patient.setAvatar(patientDTO.getAvatar());
        return patient;
    }

    public PatientDTO toDataTransferObject(Patient patient) {
        PatientDTO patientDTO = new PatientDTO();
        patient.setId(patientDTO.getId());
        patient.setName(patientDTO.getName());
        patient.setSurname(patientDTO.getSurname());
        patient.setEmail(patientDTO.getEmail());
        patient.setId(patientDTO.getId());
        patient.setAddress(patientDTO.getAddress());
        patient.setIdp(patientDTO.getIdp());
        patient.setOpd(patientDTO.getOpd());
        patient.setPhoneNumber(patientDTO.getPhoneNumber());
        patient.setNotes(patientDTO.getNotes());
        patient.setChronicPatient(patientDTO.isChronicPatient());
        patient.setLastAdmission(patientDTO.getLastAdmission());
        patient.setLastDoctorVisitedId(patientDTO.getLastDoctorVisitedId());
        patient.setPatientRecords((List)patientDTO.getPatientRecords());
        patient.setDoctorIds(patientDTO.getDoctorIds());
        patient.setAvatar(patientDTO.getAvatar());
        return patientDTO;
    }
}
