package it.intesys.dto;

import it.intesys.domain.Doctor;
import it.intesys.repository.PatientRepository;

public class DoctorMapper {

    private final PatientRepository patientRepository;

    public DoctorMapper(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }
    public Doctor toEntity(DoctorDTO patientDTO){
        Doctor doctor = new Doctor();
        doctor.setId(patientDTO.getId());
        doctor.setName(patientDTO.getName());
        doctor.setSurname(patientDTO.getSurname());
        doctor.setEmail(patientDTO.getEmail());
        doctor.setAvatar(patientDTO.getAvatar());
        doctor.setProfession(patientDTO.getProfession());
        doctor.setAddress(patientDTO.getAddress());
        doctor.setPhoneNumber(patientDTO.getPhoneNumber());
        return doctor;



    }

    public DoctorDTO toDataTransferObject(Doctor doctor){
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setId(doctor.getId());
        doctorDTO.setName(doctor.getName());
        doctorDTO.setSurname(doctor.getSurname());
        doctorDTO.setEmail(doctor.getEmail());
        doctorDTO.setAvatar(doctor.getAvatar());
        doctorDTO.setProfession(doctor.getProfession());
        doctorDTO.setAddress(doctor.getAddress());
        doctorDTO.setPhoneNumber(doctor.getPhoneNumber());
        doctorDTO.setLatestPatients(doctorDTO.getLatestPatients());
        return doctorDTO;


    }
}
