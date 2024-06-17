package it.intesys.rookie.dto;

import it.intesys.rookie.domain.Doctor;
import it.intesys.rookie.domain.Patient;
import it.intesys.rookie.repository.PatientRepository;
import it.intesys.rookie.service.NotFound;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DoctorMapper {

    private final PatientRepository patientRepository;

    public DoctorMapper(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Doctor toEntity(DoctorDTO doctorDTO){
        Doctor doctor = new Doctor();
        doctor.setId(doctorDTO.getId());
        doctor.setPhoneNumber(doctorDTO.getPhoneNumber());
        doctor.setName(doctorDTO.getName());
        doctor.setSurname(doctorDTO.getSurname());
        doctor.setEmail(doctorDTO.getEmail());
        doctor.setProfession(doctorDTO.getProfession());
        doctor.setAvatar(doctorDTO.getAvatar());
        doctor.setAddress(doctorDTO.getAddress());
        doctor.setPatients(doctorDTO.getLatestPatients().stream().map(id -> {
            Optional<Patient> optionalPatient = patientRepository.findById(id);
            if(optionalPatient.isPresent()){
                return optionalPatient.get();
            } throw new NotFound(Patient.class, id);
        }).toList());
        return doctor;

    }

    public DoctorDTO toDataTransferObject(Doctor doctor){
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setId(doctor.getId());
        doctorDTO.setPhoneNumber(doctor.getPhoneNumber());
        doctorDTO.setName(doctor.getName());
        doctorDTO.setSurname(doctor.getSurname());
        doctorDTO.setEmail(doctor.getEmail());
        doctorDTO.setProfession(doctor.getProfession());
        doctorDTO.setAvatar(doctor.getAvatar());
        doctorDTO.setAddress(doctor.getAddress());
        doctorDTO.setLatestPatients(doctor.getPatients().stream().map(Patient::getId).toList());
        return doctorDTO;
    }
}
