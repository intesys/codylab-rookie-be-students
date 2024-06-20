package it.intesys.rookie.service;

import it.intesys.rookie.domain.Doctor;
import it.intesys.rookie.domain.PatientRecord;
import it.intesys.rookie.dto.PatientRecordDTO;
import it.intesys.rookie.dto.PatientRecordMapper;
import it.intesys.rookie.repository.PatientRecordRepository;
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

        Instant now  = Instant.now();
        patientRecord.setDate(now);
        patientRecord = patientRecordRepository.save(patientRecord);
        patientRecordDTO = patientRecordMapper.toDataTransferObject(patientRecord);

        return patientRecordDTO;
    }
    public PatientRecordDTO  getPatientRecord(Long id) {
        Optional<PatientRecord> patientRecord = patientRecordRepository.findById(id);
        Optional<PatientRecordDTO> patientRecordDTO = patientRecord.map(patientRecordMapper::toDataTransferObject);
        return patientRecordDTO.orElseThrow(() -> new NotFound(PatientRecord.class, id));
    }
    public void delPatientRecord(Long id) {
        if (patientRecordRepository.findById (id).isEmpty()) {
            throw new NotFound(Doctor.class, id);
        }

        patientRecordRepository.delete (id);
    }

    public PatientRecordDTO updatePatientRecord(Long id, PatientRecordDTO patientRecordDTO) {
        if (patientRecordRepository.findById (id).isEmpty()) {
            throw new NotFound(PatientRecord.class, id);
        }

        patientRecordDTO.setId(id);
        PatientRecord patientRecord = patientRecordMapper.toEntity (patientRecordDTO);

        Instant now = Instant.now();
        patientRecord.setDate(now);

        patientRecord = patientRecordRepository.save (patientRecord);
        patientRecordDTO = patientRecordMapper.toDataTransferObject (patientRecord);
        return patientRecordDTO;

    }

    public Page<PatientRecordDTO> getPatientRecords(String filter, Pageable pageable) {
        Page<PatientRecord> patientRecords = patientRecordRepository.findAll (filter, pageable);
        return patientRecords.map(patientRecordMapper::toDataTransferObject);


    }
}
