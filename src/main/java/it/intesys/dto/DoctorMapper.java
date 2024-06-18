package it.intesys.dto;

import it.intesys.domain.Doctor;

import java.util.Optional;

public class DoctorMapper {

    private final PatientRepository patientRepository;

    public DoctorMapper(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }
     //da sistemare con i dati del doctor
    public Doctor toEntity(DoctorDTO patientDTO){
        Doctor doctor = new Doctor();
        doctor.setId(patientDTO.getId());
        doctor.setDateCreated(patientDTO.getDateCreated());
        doctor.setDateModified(patientDTO.getDateModified());
        doctor.setMembers(patientDTO.getMemberIds().stream().map(id -> {
            Optional<Patient> optionalPatient = patientRepository.findById(id);
            if (optionalPatient.isPresent())
                return optionalPatient.get();
            throw new NotFound(Patient.class, id);
        }).toList());
        return doctor;
    }

    public DoctorDTO toDataTransferObject(Doctor doctor){
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setId(doctor.getId());
        doctorDTO.setDateCreated(doctor.getDateCreated());
        doctorDTO.setDateModified(doctor.getDateModified());
        doctorDTO.setMemberIds(doctor.getMembers().stream().map(Patient::getId).toList());
        return doctorDTO;
    }
}
