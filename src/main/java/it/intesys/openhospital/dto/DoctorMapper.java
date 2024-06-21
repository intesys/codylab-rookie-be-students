package it.intesys.openhospital.dto;

import it.intesys.openhospital.domain.Doctor;
import it.intesys.openhospital.repository.PatientRepository;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {
    private PatientRepository patientRepository;
    private PatientMapper patientMapper;

    public DoctorMapper(PatientRepository patientRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
    }

    public Doctor toEntity(DoctorDTO doctorDTO){
        Doctor doctor = new Doctor();
        doctor.setId(doctorDTO.getId());
        doctor.setName(doctorDTO.getName());
        doctor.setSurname(doctorDTO.getSurname());
        doctor.setPhoneNumber(Long.valueOf(doctorDTO.getPhoneNumber()));
        doctor.setAddress(doctorDTO.getAddress());
        doctor.setEmail(doctorDTO.getEmail());
        doctor.setAvatar(doctorDTO.getAvatar());
        doctor.setProfession(doctorDTO.getProfession());
        return doctor;
    }

    public DoctorDTO toDataTransferObject(Doctor doctor){
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setId(doctor.getId());
        doctorDTO.setName(doctor.getName());
        doctorDTO.setSurname(doctor.getSurname());
        doctorDTO.setPhoneNumber(String.valueOf(doctor.getPhoneNumber()));
        doctorDTO.setAddress(doctor.getAddress());
        doctorDTO.setEmail(doctor.getEmail());
        doctorDTO.setAvatar(doctor.getAvatar());
        doctorDTO.setProfession(doctor.getProfession());
        doctorDTO.setLatestPatients(patientRepository.findLatestByDoctor(doctor, 5).stream().map(patientMapper::toDataTransferObject).toList());
        return doctorDTO;
    }
}
