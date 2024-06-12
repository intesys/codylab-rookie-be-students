package it.intesys.rookie.service;

import it.intesys.rookie.domain.Doctor;
import it.intesys.rookie.dto.DoctorDTO;
import it.intesys.rookie.dto.DoctorMapper;
import it.intesys.rookie.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    public DoctorService(DoctorRepository doctorRepository, DoctorMapper doctorMapper) {
        this.doctorRepository = doctorRepository;
        this.doctorMapper = doctorMapper;
    }

    public DoctorDTO createDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = doctorMapper.toEntity(doctorDTO);

        Instant now = Instant.now();
        doctor.setLastAdmission(now);

        doctor = doctorRepository.save(doctor);
        doctorDTO = doctorMapper.toDataTransferObject(doctor);
        return doctorDTO;
    }
}
