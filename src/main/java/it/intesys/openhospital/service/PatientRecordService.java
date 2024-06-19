package it.intesys.openhospital.service;

import it.intesys.openhospital.domain.PatientRecord;
import it.intesys.openhospital.dto.PatientRecordDTO;
import it.intesys.openhospital.dto.PatientRecordMapper;
import it.intesys.openhospital.repository.PatientRecordRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PatientRecordService {
    PatientRecordMapper patientRecordMapper;
    PatientRecordRepository patientRecordRepository;

    public PatientRecordService(PatientRecordMapper patientRecordMapper, PatientRecordRepository patientRecordRepository) {
        this.patientRecordMapper = patientRecordMapper;
        this.patientRecordRepository = patientRecordRepository;
    }

    public PatientRecordDTO createPatientRecord(PatientRecordDTO patientRecordDTO) {
        PatientRecord patientRecord = patientRecordMapper.toEntity(patientRecordDTO);
        Instant now = Instant.now();
        patientRecord.setDate(now);
        patientRecord = patientRecordRepository.save(patientRecord);
        patientRecordDTO = patientRecordMapper.toDataTransferObject(patientRecord);
        return patientRecordDTO;
    }
}
