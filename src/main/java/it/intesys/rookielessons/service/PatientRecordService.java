package it.intesys.rookielessons.service;

import it.intesys.rookielessons.domain.PatientRecord;
import it.intesys.rookielessons.dto.PatientRecordDTO;
import it.intesys.rookielessons.dto.PatientRecordMapper;
import it.intesys.rookielessons.repository.PatientRecordRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class PatientRecordService {
    private final PatientRecordRepository patientRecordRepository;
    private final PatientRecordMapper patientRecordMapper;

    public PatientRecordService(PatientRecordRepository patientRecordRepository, PatientRecordMapper patientRecordMapper) {
        this.patientRecordRepository = patientRecordRepository;
        this.patientRecordMapper = patientRecordMapper;
    }

    public PatientRecordDTO createPatientRecord(PatientRecordDTO patientRecordDTO) {
        PatientRecord patientRecord = patientRecordMapper.toEntity(patientRecordDTO);

        Instant now = Instant.now();
        patientRecord.setDate(now);

        verify (patientRecord);
        patientRecord = patientRecordRepository.save(patientRecord);
        patientRecordDTO = patientRecordMapper.toDataTransferObject(patientRecord);
        return patientRecordDTO;
    }

    public PatientRecordDTO getPatientRecord(Long id) {
        Optional<PatientRecord> patientRecord = patientRecordRepository.findById(id);
        Optional<PatientRecordDTO> patientRecordDTO = patientRecord.map(patientRecordMapper::toDataTransferObject);

        return patientRecordDTO.orElseThrow(() -> new NotFound(PatientRecord.class, id));
    }

    public PatientRecordDTO updatePatientRecord(Long id, PatientRecordDTO patientRecordDTO) {
        Optional<PatientRecord> existing = patientRecordRepository.findById(id);
        if(existing.isEmpty()){
            throw new NotFound(PatientRecord.class, id);

        }

        patientRecordDTO.setId(id);
        PatientRecord patientRecord = patientRecordMapper.toEntity(patientRecordDTO);
        verify (patientRecord);
        patientRecord = patientRecordRepository.save(patientRecord);
        patientRecordDTO = patientRecordMapper.toDataTransferObject(patientRecord);
        Instant now = Instant.now();
        patientRecord.setDate(now);
        return patientRecordDTO;
    }

    private void verify(PatientRecord patientRecord) {
    }

    public void deletePatientRecord(Long id){
        if(patientRecordRepository.findById(id).isEmpty()){
            throw new NotFound(PatientRecord.class, id);
        }

        patientRecordRepository.deletePatientRecord(id);
    }

    public Page<PatientRecordDTO> getPatientRecords(String filter, Pageable pageable) {
        Page<PatientRecord> patientRecords = patientRecordRepository.findAll (filter, pageable);
        return patientRecords.map(patientRecordMapper::toDataTransferObject);
    }
}
