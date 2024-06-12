package it.intesys.rookie.service;

import it.intesys.rookie.domain.Patient;
import it.intesys.rookie.dto.PatientDTO;
import it.intesys.rookie.dto.PatientMapper;
import it.intesys.rookie.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;


    public PatientService(PatientRepository patientRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
    }

    public PatientDTO createPatient(PatientDTO patientDTO) {
        Patient patient = patientMapper.toEntity(patientDTO);

        Instant now = Instant.now();
        patient.setLastAdmission(now);

        patient = patientRepository.save(patient);
        patientDTO = patientMapper.toDataTransferObject(patient);
        return patientDTO;
    }
}
