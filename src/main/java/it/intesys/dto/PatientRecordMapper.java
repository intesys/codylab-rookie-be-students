package it.intesys.dto;

import it.intesys.domain.Patient;
import it.intesys.domain.PatientRecord;
import org.springframework.stereotype.Component;

@Component
public class PatientRecordMapper {
    public PatientRecord toEntity(PatientRecordDTO patientRecordDTO) {
        PatientRecord patientRecord = new PatientRecord();
        patientRecord.setId(patientRecordDTO.getId());
        patientRecord.setName(patientRecordDTO.getName());
        patientRecord.setSurname(patientRecordDTO.getSurname());
        patientRecord.setEmail(patientRecordDTO.getEmail());
        return patientRecord;
    }

    public PatientRecordDTO toDataTrasferObject(PatientRecord patientRecord) {
        PatientRecordDTO patientRecordDTO = new PatientRecordDTO();
        patientRecordDTO.setId(patientRecord.getId());
        patientRecordDTO.setName(patientRecord.getName());
        patientRecordDTO.setSurname(patientRecord.getSurname());
        patientRecordDTO.setEmail(patientRecord.getEmail());
        return patientRecordDTO;

    }
}
