package hospital.dto;

import hospital.domain.Patient;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class PatientMapper {
    public Patient toEntity(PatientDTO patientDTO){
        Patient patient = new Patient();
        patient.setId(patientDTO.getId());
        patient.setLastVisit(patientDTO.getDateCreated());
        patient.setLastOperation(patientDTO.getLastOperation());
        patient.setName(patientDTO.getName());
        patient.setSurname(patientDTO.getSurname());
        patient.setEmail(patientDTO.getEmail());


        patient.setId(patientDTO.getId());
        patient.setAddress(patientDTO.getAddress());
        patient.getIdp(patientDTO.getIdp());
        patient.setOpd(patientDTO.getOpd());
        patient.setPhoneNumber(patientDTO.getPhoneNumber());
        patient.setNotes(patientDTO.getNotes());
        patient.setChronicPatient(patientDTO.get);
        patient.setLastAdmission(patientDTO.getLastAdmission());
        patient.setLastDoctorVisitedId(patientDTO.getLastDoctorVisitedId());
        patient.setPatientRecords((List)patientDTO.getPatientRecords());
        patient.setDoctorIds(patientDTO.getDoctorIds());
        patient.setAvatar(patientDTO.getAvatar());
        patient.setName(resultSet.getString("name"));
        patient.setSurname(resultSet.getString("surname"));
        patient.setEmail(resultSet.getString("email"));
    }

    public PatientDTO toDataTransferObject(Patient patient) {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(patient.getId());
        patientDTO.setLastOperation(patient.getLastVisit());
        patientDTO.setLastVisit(patient.getLastVisit());
        patientDTO.setName(patient.getName());
        patientDTO.setSurname(patient.getSurname());
        patientDTO.setEmail(patient.getEmail());
        return patientDTO;

    }
}
