package it.intesys.openhospital.service;

import it.intesys.openhospital.domain.Patient;
import it.intesys.openhospital.dto.PatientDTO;
import it.intesys.openhospital.dto.PatientFilterDTO;
import it.intesys.openhospital.dto.PatientMapper;
import it.intesys.openhospital.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        patient = patientRepository.save(patient);
        patientDTO = patientMapper.toDataTransferObject(patient);
        return patientDTO;
    }

    public PatientDTO getPatient(Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        Optional<PatientDTO> patientTDO = patient.map(patientMapper::toDataTransferObject);
        return patientTDO.orElseThrow(() -> new NotFound(Patient.class, id));
    }

    public PatientDTO updatePatient(Long id, PatientDTO patientDTO) {
        if (patientRepository.findById(id).isEmpty()){
            throw new NotFound(Patient.class, id);
        }
        patientDTO.setId(id);
        Patient patient = patientMapper.toEntity(patientDTO);
        patient = patientRepository.save(patient);
        patientDTO = patientMapper.toDataTransferObject(patient);
        return patientDTO;
    }

    public Page<PatientDTO> getPatients(PatientFilterDTO filter, Pageable pageable) {
        Page<Patient> patients = patientRepository.findAll(filter.getText(), filter.getId(), filter.getOpd(), filter.getIdp(), filter.getDoctorId(), pageable);
        return patients.map(patientMapper::toDataTransferObject);
    }

    public void deletePatient(Long id) {
        if (patientRepository.findById(id).isEmpty()){
            throw new NotFound(Patient.class, id);
        }
        patientRepository.delete(id);
    }

}


