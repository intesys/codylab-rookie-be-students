package it.intesys.rookielessons.dto;

import it.intesys.rookielessons.domain.Doctor;
import it.intesys.rookielessons.repository.PatientRepository;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {
    private final PatientRepository patientRepository;

    public DoctorMapper(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Doctor toEntity(DoctorDTO doctorDTO){
        Doctor doctor = new Doctor();
        doctor.setId(doctorDTO.getId());
        doctor.setName(doctorDTO.getName());
        doctor.setSurname(doctorDTO.getSurname());
        doctor.setEmail(doctorDTO.getEmail());
        doctor.setPhoneNumber(doctorDTO.getPhoneNumber());
        doctor.setAddress(doctorDTO.getAddress());
        doctor.setAvatar(doctorDTO.getAvatar());
        doctor.setProfession(doctorDTO.getProfession());
        return doctor;
    }

    public DoctorDTO toDataTransferObject(Doctor doctor){
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setId(doctor.getId());
        doctorDTO.setName(doctor.getName());
        doctorDTO.setSurname(doctor.getSurname());
        doctorDTO.setEmail(doctor.getEmail());
        doctorDTO.setPhoneNumber(doctor.getPhoneNumber());
        doctorDTO.setAddress(doctor.getAddress());
        doctorDTO.setAvatar(doctor.getAvatar());
        doctorDTO.setProfession(doctor.getProfession());
        return doctorDTO;
    }
}
