package it.intesys.rookie.service;

import it.intesys.rookie.domain.PatientRecord;
import it.intesys.rookie.dto.PatientRecordDTO;
import it.intesys.rookie.dto.PatientRecordMapper;
import it.intesys.rookie.repository.PatientRecordRepository;
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

        patientRecord = patientRecordRepository.save(patientRecord);
        patientRecordDTO = patientRecordMapper.toDataTransferObject(patientRecord);
        return patientRecordDTO;
    }

    public PatientRecordDTO getPatientRecord(Long id) {
        Optional<PatientRecord> patientRecord = patientRecordRepository.findById(id);
        Optional<PatientRecordDTO> patientRecordDTO = patientRecord.map(patientRecordMapper::toDataTransferObject);

        PatientRecordDTO result = patientRecordDTO.orElseThrow(() -> new NotFound(PatientRecord.class, id));
        return result;
    }

    public PatientRecordDTO updatePatientRecord(Long id, PatientRecordDTO patientRecordDTO) {
        if(patientRecordRepository.findById(id).isEmpty()){
            throw new NotFound(PatientRecord.class, id);
        }

        patientRecordDTO.setId(id);
        PatientRecord patientRecord = patientRecordMapper.toEntity(patientRecordDTO);

        Instant now = Instant.now();
        patientRecord.setDate(now);

        patientRecord = patientRecordRepository.save(patientRecord);
        patientRecordDTO = patientRecordMapper.toDataTransferObject(patientRecord);
        return patientRecordDTO;
    }

    public PatientRecordDTO deletePatientRecord(Long id) {
        Optional<PatientRecord> patientRecord = patientRecordRepository.deletePatientRecord(id);
        Optional<PatientRecordDTO> patientRecordDTO = patientRecord.map(patientRecordMapper::toDataTransferObject);

        PatientRecordDTO result = patientRecordDTO.orElseThrow(() -> new NotFound(PatientRecord.class, id));
        return result;
    }

//    public Page<PatientRecordDTO> getPatientRecords(PatientRecordFilterDTO filter, Pageable pageable) {
//        Page<PatientRecord> patientRecord = patientRecordRepository.findAll(filter.getId(), filter.getOpd(), filter.getIdp(), filter.getDoctorId(), filter.getText(), pageable);
//        return patientRecord.map(patientRecordMapper::toDataTransferObject);
//    }
}
