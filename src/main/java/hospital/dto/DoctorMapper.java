package hospital.dto;

import hospital.domain.Doctor;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {
    public Doctor toEntity(DoctorDTO doctorDTO){
        Doctor doctor = new Doctor();
        doctor.setId(doctorDTO.getId());
        doctor.setPhoneNumber(doctorDTO.getPhoneNumber());
        doctor.setProfession(doctorDTO.getProfession());
        doctor.setName(doctorDTO.getName());
        doctor.setSurname(doctorDTO.getSurname());
        doctor.setEmail(doctorDTO.getEmail());
        doctor.setAddress (doctorDTO.getAddress());
        return doctor;
    }

    public DoctorDTO toDataTransferObject(Doctor doctor) {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setId(doctor.getId());
        doctorDTO.setPhoneNumber(doctor.getPhoneNumber());
        doctorDTO.setProfession(doctor.getProfession());
        doctorDTO.setName(doctor.getName());
        doctorDTO.setSurname(doctor.getSurname());
        doctorDTO.setEmail(doctor.getEmail());
        doctorDTO.setAddress (doctor.getAddress());
        return doctorDTO;

    }
}