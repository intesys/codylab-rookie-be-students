package it.intesys.rookie.service;

import it.intesys.rookie.domain.Doctor;
import it.intesys.rookie.dto.DoctorDTO;
import it.intesys.rookie.dto.DoctorFilterDTO;
import it.intesys.rookie.dto.DoctorMapper;
import it.intesys.rookie.dto.PatientDTO;
import it.intesys.rookie.repository.DoctorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        doctor = doctorRepository.save(doctor);
        doctorDTO = doctorMapper.toDataTransferObject(doctor);
        return doctorDTO;
    }

    public Page<DoctorDTO> getDoctors(DoctorFilterDTO filter, Pageable pageable) {
        Page<Doctor> doctors = doctorRepository.findAll (filter.getName(), filter.getSurname(), filter.getProfession(), pageable);
        return doctors.map(doctorMapper::toDataTransferObject);
    }
}

