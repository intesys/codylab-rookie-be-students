package it.intesys.rookielessons.service;

import it.intesys.rookielessons.domain.Patient;
import it.intesys.rookielessons.dto.PatientDTO;
import it.intesys.rookielessons.dto.PatientMapper;
import it.intesys.rookielessons.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public PatientDTO createPatient(PatientDTO patientDTO) {
        Patient patient = patientMapper.toEntity(patientDTO);

        Instant now = Instant.now();
        patient.setLastAdmission(now);


        verify (patient);

        patient = patientRepository.save(patient);
        patientDTO = patientMapper.toDataTransferObject(patient);
        return patientDTO;
    }

    public PatientDTO getPatient(Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        Optional<PatientDTO> patientDTO = patient.map(patientMapper::toDataTransferObject);

        return patientDTO.orElseThrow(() -> new NotFound(Patient.class, id));
    }

    public PatientDTO updatePatient(Long id, PatientDTO patientDTO) {
        Optional<Patient> existing = patientRepository.findById(id);
        if(existing.isEmpty()){
            throw new NotFound(Patient.class, id);
        }

        patientDTO.setId(id);
        Patient patient = patientMapper.toEntity(patientDTO);

        Instant now = Instant.now();
        patient.setLastAdmission(now);

        verify (patient);
        patient = patientRepository.save(patient);
        patientDTO = patientMapper.toDataTransferObject(patient);
        return patientDTO;
    }

    private void verify(Patient patient) {
        if (patient.getPhoneNumber() == null)
            throw new Mandatory(Patient.class, patient.getId(), "phoneNumber");
        if (patient.getName() == null)
            throw new Mandatory(Patient.class, patient.getId(), "name");
        if (patient.getSurname() == null)
            throw new Mandatory(Patient.class, patient.getId(), "surname");
        if (patient.getEmail() == null)
            throw new Mandatory(Patient.class, patient.getId(), "email");
    }

    public void deletePatient(Long id){
        if(patientRepository.findById(id).isEmpty()){
            throw new NotFound(Patient.class, id);
        }

        patientRepository.deletePatient(id);
    }

    public Page<PatientDTO> getPatients(String filter, Pageable pageable) {
        Page<Patient> patients = patientRepository.findAll (filter, pageable);
        return patients.map(patientMapper::toDataTransferObject);
    }
}
