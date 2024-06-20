package it.intesys.service;

import it.intesys.domain.Patient;
import it.intesys.domain.PatientRecord;
import it.intesys.dto.PatientDTO;
import it.intesys.dto.PatientMapper;
import it.intesys.dto.PatientRecordDTO;
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

    public PatientRecordDTO createPatient(PatientRecordDTO patientRecordDTO) {
        PatientRecord patientRecord = patientRecordMapper.toEntity (patientRecordDTO);

        Instant now = Instant.now();
        patientRecord.setUltima_visita(Instant.now());

        patientRecord = patientRecordRepository.save (patientRecord);
        patientRecordDTO = patientRecordMapper.toDataTrasferObject (patientRecord);
        return patientDTO;
    }

    //codici pomeriggio 13 giugno

    public PatientRecordDTO getPatient (Long id){
        Optional<PatientRecord> patientRecord = patientRecordRepository.findById (id);
        Optional<PatientRecordDTO> patientRecordDTO = patientRecord.map(patientRecordMapper::toDataTrasferObject);
        return patientRecordDTO.orElseThrow(() -> new NotFound(PatientRecord.class, id));
    }

    //codici pomeriggio 14 giugno

    public PatientRecordDTO updatePatient (Long id, PatientRecordDTO patientRecordDTO) {
        if (patientRecordRepository.findById(id).isEmpty()){
            throw new NotFound(PatientRecord.class, id);
        }
        patientRecordDTO.setId(id);
        PatientRecord patientRecord = patientRecordMapper.toEntity(patientRecordDTO);

        Instant now = Instant.now();
        patientRecord.setUltima_visita(now);

        patientRecord = patientRecordRepository.save (patientRecord);
        patientRecordDTO = patientRecordMapper.toDataTrasferObject(patientRecord);
        return patientRecordDTO;
    }
    public void deletePatient (Long id) {
        if(patientRecordRepository.findById (id).isEmpty()){
            throw new NotFound(PatientRecord.class, id);
        }
        patientRecordRepository.delete (id);
    }

    public Page<PatientRecordDTO> getPatients(String filter, Pageable pageable) {
        Page<PatientRecord> patientsRecord = patienRecordtRepository.findAll (filter,pageable);
        return patientsRecord.map(patientRecordMapper::toDataTrasferObject);
    }
}
