package it.intesys.rookie.service;

import it.intesys.rookie.domain.Doctor;
import it.intesys.rookie.domain.Patient;
import it.intesys.rookie.dto.DoctorDTO;
import it.intesys.rookie.dto.DoctorMapper;
import it.intesys.rookie.dto.PatientDTO;
import it.intesys.rookie.repository.DoctorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.time.Instant;
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

    public DoctorDTO  getDoctor(Long id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        Optional<DoctorDTO> doctorDTO = doctor.map(doctorMapper::toDataTransferObject);
        return doctorDTO.orElseThrow(() -> new NotFound(Doctor.class, id));
    }
    public void delDoctor(Long id) {
        if (doctorRepository.findById (id).isEmpty()) {
            throw new NotFound(Doctor.class, id);
        }

        doctorRepository.delete (id);
    }

    public DoctorDTO updateDoctor(Long id, DoctorDTO doctorDTO) {
        if (doctorRepository.findById (id).isEmpty()) {
            throw new NotFound(Doctor.class, id);
        }

        doctorDTO.setId(id);
        Doctor doctor = doctorMapper.toEntity (doctorDTO);

        doctor = doctorRepository.save (doctor);
        doctorDTO = doctorMapper.toDataTransferObject (doctor);
        return doctorDTO;
    }

    public Page<DoctorDTO> getDoctors(String filter, Pageable pageable) {
        Page<Doctor> doctors = doctorRepository.findAll (filter, pageable);
        return doctors.map(doctorMapper::toDataTransferObject);
    }
}
