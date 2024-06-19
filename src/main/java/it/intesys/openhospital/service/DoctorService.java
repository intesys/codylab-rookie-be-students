package it.intesys.openhospital.service;

import it.intesys.openhospital.domain.Doctor;
import it.intesys.openhospital.dto.DoctorDTO;
import it.intesys.openhospital.dto.DoctorMapper;
import it.intesys.openhospital.repository.DoctorRepository;
import it.intesys.openhospital.dto.DoctorFilterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
 import java.util.Optional;

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

    public DoctorDTO getDoctor(Long id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        Optional<DoctorDTO> doctorTDO = doctor.map(doctorMapper::toDataTransferObject);
        return doctorTDO.orElseThrow(() -> new NotFound(Doctor.class, id));
    }

    public DoctorDTO updateDoctor(Long id, DoctorDTO doctorDTO) {
        if (doctorRepository.findById(id).isEmpty()){
            throw new NotFound(Doctor.class, id);
        }
        doctorDTO.setId(id);
        Doctor doctor = doctorMapper.toEntity(doctorDTO);
        doctor = doctorRepository.save(doctor);
        doctorDTO = doctorMapper.toDataTransferObject(doctor);
        return doctorDTO;
    }

    public Page<DoctorDTO> getDoctors(DoctorFilterDTO filter, Pageable pageable) {
        Page<Doctor> doctors = doctorRepository.findAll(filter.getName(), filter.getSurname(), filter.getProfession(), pageable);
        return doctors.map(doctorMapper::toDataTransferObject);
    }

    public void deleteDoctor(Long id) {
        if (doctorRepository.findById(id).isEmpty()){
            throw new NotFound(Doctor.class, id);
        }
        doctorRepository.delete(id);
    }
}
