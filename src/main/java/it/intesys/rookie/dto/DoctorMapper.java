package it.intesys.rookie.dto;

import it.intesys.rookie.domain.Doctor;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {
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
        return doctorDTO;
    }
}
