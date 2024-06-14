package it.intesys.rookie.service;

import it.intesys.rookie.domain.Doctor;
import it.intesys.rookie.domain.Patient;
import it.intesys.rookie.dto.PatientDTO;
import it.intesys.rookie.dto.PatientFilterDTO;
import it.intesys.rookie.dto.PatientMapper;
import it.intesys.rookie.repository.PatientRepository;
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

        patient = patientRepository.save(patient);
        patientDTO = patientMapper.toDataTransferObject(patient);
        return patientDTO;
    }

    public PatientDTO getPatient(Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        Optional<PatientDTO> patientDTO = patient.map(patientMapper::toDataTransferObject);

        PatientDTO result = patientDTO.orElseThrow(() -> new NotFound(Patient.class, id));
        return result;
    }

    public PatientDTO updatePatient(Long id, PatientDTO patientDTO) {
        if(patientRepository.findById(id).isEmpty()){
            throw new NotFound(Patient.class, id);
        }

        patientDTO.setId(id);
        Patient patient = patientMapper.toEntity(patientDTO);

        Instant now = Instant.now();
        patient.setLastAdmission(now);

        patient = patientRepository.save(patient);
        patientDTO = patientMapper.toDataTransferObject(patient);
        return patientDTO;
    }

    public PatientDTO deletePatient(Long id) {
        Optional<Patient> patient = patientRepository.deletePatient(id);
        Optional<PatientDTO> patientDTO = patient.map(patientMapper::toDataTransferObject);

        PatientDTO result = patientDTO.orElseThrow(() -> new NotFound(Doctor.class, id));
        return result;
    }

    public Page<PatientDTO> getPatients(PatientFilterDTO filter, Pageable pageable) {
        Page<Patient> patient = patientRepository.findAll(filter.getId(), filter.getOpd(), filter.getIdp(), filter.getDoctorId(), filter.getText(), pageable);
        return patient.map(patientMapper::toDataTransferObject);
    }
}