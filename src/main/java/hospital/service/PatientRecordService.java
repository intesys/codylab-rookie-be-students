package hospital.service;

import hospital.domain.Doctor;
import hospital.domain.Patient;
import hospital.domain.PatientRecord;
import hospital.dto.PatientRecordDTO;
import hospital.dto.PatientRecordMapper;
import hospital.repository.PatientRecordRepository;

import java.time.Instant;
import java.util.Optional;

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

    public PatientRecordDTO getPatientRecord(Long id) {
        Optional<PatientRecord> patientRecord = patientRecordRepository.findById(id);
        Optional<PatientRecordDTO> patientRecordDTO = patientRecord.map(patientRecordMapper::toDataTransferObject);
        return patientRecordDTO.orElseThrow(() -> new NotFound(Doctor.class, id));
    }

    public PatientRecordDTO updatePatientRecord(Long id, PatientRecordDTO patientRecordDTO) {
        if (patientRecordRepository.findById(id).isEmpty()){
            throw new NotFound(Patient.class, id);
        }
        patientRecordDTO.setId(id);
        Instant now = Instant.now();
        patientRecordDTO.setDate(now);
        PatientRecord patientRecord = patientRecordMapper.toEntity(patientRecordDTO);
        patientRecord = patientRecordRepository.save(patientRecord);
        patientRecordDTO = patientRecordMapper.toDataTransferObject(patientRecord);
        return patientRecordDTO;
    }

    public void deleteDoctor(Long id) {
        if (patientRecordRepository.findById(id).isEmpty()){
            throw new NotFound(Doctor.class, id);
        }
        patientRecordRepository.delete(id);
    }
}
