package it.intesys.dto;

import it.intesys.domain.Patient;
import it.intesys.domain.PatientRecord;
import org.springframework.stereotype.Component;

@Component
public class PatientRecordMapper {
    public PatientRecord toEntity(PatientRecordDTO patientRecordDTO) {
        PatientRecord patientRecord = new PatientRecord();
        patientRecord.setId(patientRecordDTO.getId());
        patientRecord.setPatientId(patientRecordDTO.getPatientId());
        patientRecord.setDoctorId(patientRecordDTO.getDoctorId());
        patientRecord.setDate(patientRecordDTO.getDate());
        patientRecord.setReasonVisit(patientRecord.getReasonVisit());
        patientRecord.setTypeVisit(patientRecord.getTypeVisit());
        patientRecord.setTreatmentMade(patientRecord.getTreatmentMade());
        return patientRecord;
    }

    public PatientRecordDTO toDataTrasferObject(PatientRecord patientRecord) {
        PatientRecordDTO patientRecordDTO = new PatientRecordDTO();
        patientRecordDTO.setId(patientRecord.getId());
        patientRecordDTO.setDate(patientRecord.getDate());
        patientRecordDTO.setReasonVisit(patientRecord.getReasonVisit());
        patientRecordDTO.setTypeVisit(patientRecord.getTypeVisit());
        patientRecordDTO.setTreatmentMade(patientRecord.getTreatmentMade());
        return patientRecordDTO;

    }
}
