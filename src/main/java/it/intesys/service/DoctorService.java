package it.intesys.service;

import it.intesys.domain.Doctor;
import it.intesys.dto.DoctorDTO;
import it.intesys.dto.DoctorMapper;
import it.intesys.repository.DoctorRepository;
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

        verify (doctor);

        doctor = doctorRepository.save(doctor);
        doctorDTO = doctorMapper.toDataTransferObject(doctor);
        return doctorDTO;
    }

    public DoctorDTO getDoctor(Long id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        Optional<DoctorDTO> doctorDTO = doctor.map(doctorMapper::toDataTransferObject);

        DoctorDTO result = doctorDTO.orElseThrow(() -> new NotFound(Doctor.class, id));
        return result;
    }

    public DoctorDTO updateDoctor(Long id, DoctorDTO doctorDTO) {
        Optional<Doctor> existing = doctorRepository.findById(id);
        if(existing.isEmpty()){
            throw new NotFound(Doctor.class, id);
        }

        doctorDTO.setId(id);
        Doctor doctor = doctorMapper.toEntity(doctorDTO);


        verify (doctor);
        doctor = doctorRepository.save(doctor);
        doctorDTO = doctorMapper.toDataTransferObject(doctor);
        return doctorDTO;
    }

    private void verify(Doctor doctor) {
    }

    public void deleteDoctor(Long id){
        if(doctorRepository.findById(id).isEmpty()){
            throw new NotFound(Doctor.class, id);
        }

        doctorRepository.deleteDoctor(id);
    }

    public Page<DoctorDTO> getDoctors(String filter, Pageable pageable) {
        Page<Doctor> doctors = doctorRepository.findAll (filter, pageable);
        return doctors.map(doctorMapper::toDataTransferObject);
    }
}
