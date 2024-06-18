package it.intesys.service;

import it.intesys.domain.Patient;
import it.intesys.dto.PatientDTO;
import it.intesys.dto.PatientMapper;
import it.intesys.reposity.PatientRepository;
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
        Patient patient = patientMapper.toEntity (patientDTO);

        Instant now = Instant.now();
        patient.setUltima_visita(Instant.now());

        patient = patientRepository.save (patient);
        patientDTO = patientMapper.toDataTrasferObject (patient);
        return patientDTO;
    }

    //codici pomeriggio 13 giugno

    public PatientDTO getPatient (Long id){
        Optional<Patient> patient = patientRepository.findById (id);
        Optional<PatientDTO> patientDTO = patient.map(patientMapper::toDataTrasferObject);
        return patientDTO.orElseThrow(() -> new NotFound(Patient.class, id));
    }

    //codici pomeriggio 14 giugno

    public PatientDTO updatePatient (Long id, PatientDTO patientDTO) {
        if (patientRepository.findById(id).isEmpty()){
            throw new NotFound(Patient.class, id);
        }
        patientDTO.setId(id);
        Patient patient = patientMapper.toEntity(patientDTO);

        Instant now = Instant.now();
        patient.setUltima_visita(now);

        patient = patientRepository.save (patient);
        patientDTO = patientMapper.toDataTrasferObject(patient);
        return patientDTO;
    }
    public void deletePatient (Long id) {
        if(patientRepository.findById (id).isEmpty()){
            throw new NotFound(Patient.class, id);
        }
        patientRepository.delete (id);
    }

    public Page<PatientDTO> getPatients(String filter, Pageable pageable) {
        Page<Patient> patients = patientRepository.findAll (filter,pageable);
        return patients.map(patientMapper::toDataTrasferObject);
    }

}
