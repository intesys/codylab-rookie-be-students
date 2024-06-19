package hospital.service;

import hospital.domain.Patient;
import hospital.dto.PatientDTO;
import hospital.dto.PatientMapper;
import hospital.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import src.main.chat.domain.Patient;
import src.main.chat.dto.PatientMapper;
import src.main.chat.repository.PatientRepository;
import src.main.chat.dto.PatientDTO;

import java.time.Instant;
import java.util.Optional;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public PatientService(PatientRepository patientRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
    }

    public PatientDTO createpatient(PatientDTO patientDTO){
        Patient patient = patientMapper.toEntity (patientDTO);

        Instant now = Instant.now();
        patient.setDateCreated(now);
        patient.setDateModified(now);

        patient = patientRepository.save (patient);
        patientDTO = patientMapper.toDataTransferObject (patient);
        return patientDTO;
    }
    public PatientDTO getpatient(Long id) {
        Optional<Patient> patient = patientRepository.findById (id);
        Optional<PatientDTO> patientDTO = patient.map(patientMapper::toDataTransferObject);
        return patientDTO.orElseThrow(() -> new NotFound(patient.class, id));
    }

    public PatientDTO updatePatient (Long id, PatientDTO patientDTO){
        if (patientRepository.findById(id).isEmpty()){
            throw new NotFound(Patient.class, id);
        }
        Patient patient = patientMapper.toEntity (patientDTO);

        Instant now = Instant.now();
        patient.setDateModified(now);

        patient = patientRepository.save (patient);
        patientDTO = patientMapper.toDataTransferObject (patient);
        return patientDTO;
    }

    public void deletePatient (Long id) {
        if(patientRepository.findById(id).isEmpty()){
            throw new NotFound(Patient.class, id);
        }
        patientRepository.delete(id);
    }

    public Page<PatientDTO> getPatients(String filter, Pageable pageable) {
        Page<Patient> patients = patientRepository.findAll (filter, pageable);
        return patients.map(patientMapper::toDataTransferObject);
    }
}
