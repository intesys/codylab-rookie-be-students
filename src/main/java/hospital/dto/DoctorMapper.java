package hospital.dto;

import hospital.domain.Doctor;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {
    public Doctor toEntity(DoctorDTO doctorDTO){
        Doctor doctor = new Doctor();
        doctorDTO.setId(doctor.getId());
        doctorDTO.setPhone(doctor.getPhoneNumber());
        doctorDTO.setProfession(doctor.getProfession());
        doctorDTO.setName(doctor.getName());
        doctorDTO.setSurname(doctor.getSurname());
        doctorDTO.setEmail(doctor.getEmail());
        return doctor;
    }

    public DoctorDTO toDataTransferObject(Doctor doctor) {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setId(doctor.getId());
        doctorDTO.setPhone(doctor.getPhoneNumber());
        doctorDTO.setProfession(doctor.getProfession());
        doctorDTO.setName(doctor.getName());
        doctorDTO.setSurname(doctor.getSurname());
        doctorDTO.setEmail(doctor.getEmail());
        return doctorDTO;

    }
}