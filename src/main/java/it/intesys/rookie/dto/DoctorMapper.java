package it.intesys.rookie.dto;
import it.intesys.rookie.repository.PatientRepository;
import it.intesys.rookie.domain.Doctor;
import org.springframework.stereotype.Component;

@Component

public class DoctorMapper {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public DoctorMapper(PatientRepository patientRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
    }

    public Doctor toEntity(DoctorDTO doctorDTO) {
        Doctor doctor = new Doctor();
        doctor.setId(doctorDTO.getId());
        doctor.setName(doctorDTO.getName());
        doctor.setSurname(doctorDTO.getSurname());
        doctor.setEmail(doctorDTO.getEmail());
        doctor.setPhoneNumber(Long.valueOf(doctorDTO.getPhoneNumber()));
        doctor.setProfession(doctorDTO.getProfession());
        doctor.setAvatar(doctorDTO.getAvatar());
        doctor.setAddress(doctorDTO.getAddress());
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
        doctorDTO.setAvatar(doctor.getAvatar());
        doctorDTO.setAddress(doctor.getAddress());
        doctorDTO.setLatestPatients(patientRepository.findLatestByDoctor(doctor, 5).stream().map(patientMapper::toDataTransferObject).toList());
        return doctorDTO;

    }
}
