package it.intesys.rookielessons.dto;

import it.intesys.rookielessons.domain.PatientRecord;
import org.springframework.stereotype.Component;

@Component
public class PatientRecordMapper {

    public PatientRecord toEntity(PatientRecordDTO patientRecordDTO){
        PatientRecord patientRecord = new PatientRecord();
        patientRecord.setId(patientRecordDTO.getId());
        patientRecord.setPatientId(patientRecordDTO.getPatientId());
        patientRecord.setDoctorId(patientRecordDTO.getDoctorId());
        patientRecord.setTypeVisit(patientRecordDTO.getTypeVisit());
        patientRecord.setReasonVisit(patientRecordDTO.getReasonVisit());
        patientRecord.setTreatmentMade(patientRecordDTO.getTreatmentMade());
        patientRecord.setDate(patientRecordDTO.getDate());
        return patientRecord;

    }

    public PatientRecordDTO toDataTransferObject(PatientRecord patientRecord){
        PatientRecordDTO patientRecordDTO = new PatientRecordDTO();
        patientRecordDTO.setId(patientRecord.getId());
        patientRecordDTO.setPatientId(patientRecord.getPatientId());
        patientRecordDTO.setDoctorId(patientRecord.getDoctorId());
        patientRecordDTO.setTypeVisit(patientRecord.getTypeVisit());
        patientRecordDTO.setReasonVisit(patientRecord.getReasonVisit());
        patientRecordDTO.setTreatmentMade(patientRecord.getTreatmentMade());
        patientRecordDTO.setDate(patientRecord.getDate());
        return patientRecordDTO;

    }
}
