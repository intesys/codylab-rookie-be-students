package it.intesys.rookie.dto;

import it.intesys.rookie.domain.Doctor;
import org.springframework.stereotype.Component;

@Component

public class DoctorMapper {
    public Doctor toEntity(DoctorDTO doctorDTO) {
        Doctor doctor = new Doctor();
        doctor.setAddress(doctorDTO.getAddress());
        doctor.setAvatar(doctorDTO.getAvatar ());
        doctor.setId(doctorDTO.getId());
        doctor.setName(doctorDTO.getName());
        doctor.setSurname(doctorDTO.getSurname());
        doctor.setEmail(doctorDTO.getEmail());
        doctor.setPhoneNumber(Long.parseLong(doctorDTO.getPhoneNumber()));
        doctor.setProfession(doctorDTO.getProfession());
        return doctor;
    }

    public DoctorDTO toDataTransferObject(Doctor doctor) {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setId(doctor.getId());
        doctorDTO.setName(doctor.getName());
        doctorDTO.setSurname(doctor.getSurname());
        doctorDTO.setEmail(doctor.getEmail());
        doctorDTO.setPhoneNumber(String.valueOf(doctor.getPhoneNumber()));
        doctorDTO.setProfession(doctor.getProfession());
        doctorDTO.setAddress(doctor.getAddress());
        doctorDTO.setAvatar(doctor.getAvatar());

        return doctorDTO;
    }
}