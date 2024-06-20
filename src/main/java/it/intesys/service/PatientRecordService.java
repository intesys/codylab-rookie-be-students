package it.intesys.service;

import it.intesys.domain.Patient;
import it.intesys.domain.PatientRecord;
import it.intesys.dto.PatientDTO;
import it.intesys.dto.PatientMapper;
import it.intesys.dto.PatientRecordDTO;
import it.intesys.dto.PatientRecordMapper;
import it.intesys.repository.PatientRecordRepository;
import it.intesys.repository.PatientRepository;
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
        PatientRecord patientRecord = patientRecordMapper.toEntity (patientRecordDTO);

        Instant now = Instant.now();
        patientRecord.setDate(Instant.now());

        patientRecord = patientRecordRepository.save (patientRecord);
        patientRecordDTO = patientRecordMapper.toDataTrasferObject (patientRecord);
        return patientRecordDTO;
    }

    //codici pomeriggio 13 giugno

    public PatientRecordDTO getPatientRecord (Long id){
        Optional<PatientRecord> patientRecord = patientRecordRepository.findById (id);
        Optional<PatientRecordDTO> patientRecordDTO = patientRecord.map(patientRecordMapper::toDataTrasferObject);
        return patientRecordDTO.orElseThrow(() -> new NotFound(PatientRecord.class, id));
    }

    //codici pomeriggio 14 giugno

    public PatientRecordDTO updatePatientRecord (Long id, PatientRecordDTO patientRecordDTO) {
        if (patientRecordRepository.findById(id).isEmpty()){
            throw new NotFound(PatientRecord.class, id);
        }
        patientRecordDTO.setId(id);
        PatientRecord patientRecord = patientRecordMapper.toEntity(patientRecordDTO);

        Instant now = Instant.now();
        patientRecord.setDate(now);

        patientRecord = patientRecordRepository.save (patientRecord);
        patientRecordDTO = patientRecordMapper.toDataTrasferObject(patientRecord);
        return patientRecordDTO;
    }
    public void deletePatientRecord (Long id) {
        if(patientRecordRepository.findById (id).isEmpty()){
            throw new NotFound(PatientRecord.class, id);
        }
        patientRecordRepository.delete (id);
    }
}
