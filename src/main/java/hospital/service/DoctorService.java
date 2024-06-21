package hospital.service;

import hospital.domain.Doctor;
import hospital.dto.DoctorDTO;
import hospital.dto.DoctorFilterDTO;
import hospital.dto.DoctorMapper;
import hospital.repository.DoctorRepository;
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

    public DoctorDTO createdoctor(DoctorDTO doctorDTO){
        Doctor doctor = doctorMapper.toEntity (doctorDTO);
        doctor = doctorRepository.save (doctor);
        doctorDTO = doctorMapper.toDataTransferObject (doctor);
        return doctorDTO;
    }
    public DoctorDTO getdoctor(Long id) {
        Optional<Doctor> doctor = doctorRepository.findById (id);
        Optional<DoctorDTO> doctorDTO = doctor.map(doctorMapper::toDataTransferObject);
        return doctorDTO.orElseThrow(() -> new NotFound(Doctor.class, id));
    }

    public DoctorDTO updateDoctor (Long id, DoctorDTO doctorDTO){
        if (doctorRepository.findById(id).isEmpty()){
            throw new NotFound(Doctor.class, id);
        }
        Doctor doctor = doctorMapper.toEntity (doctorDTO);


        doctor = doctorRepository.save (doctor);
        doctorDTO = doctorMapper.toDataTransferObject (doctor);
        return doctorDTO;
    }

    public void deleteDoctor (Long id) {
        if(doctorRepository.findById(id).isEmpty()){
            throw new NotFound(Doctor.class, id);
        }
        doctorRepository.delete(id);
    }

    public Page<DoctorDTO> getDoctors(DoctorFilterDTO filter, Pageable pageable) {
        Page<Doctor> doctors = doctorRepository.findAll (filter.getName(), filter.getProfession(), filter.getProfession(), pageable);
        return doctors.map(doctorMapper::toDataTransferObject);
    }

    public DoctorDTO createDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = doctorMapper.toEntity (doctorDTO);
        doctor = doctorRepository.save (doctor);
        doctorDTO = doctorMapper.toDataTransferObject (doctor);
        return doctorDTO;

    }

    public DoctorDTO getDoctor(Long id) {
        return doctorRepository.findById(id).map(doctorMapper::toDataTransferObject).orElseThrow(() -> new NotFound(Doctor.class, id));
    }
}
