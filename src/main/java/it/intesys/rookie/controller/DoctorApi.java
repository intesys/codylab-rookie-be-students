package it.intesys.rookie.controller;
import it.intesys.rookie.domain.Doctor;
import it.intesys.rookie.dto.DoctorDTO;
import it.intesys.rookie.dto.PatientDTO;
import it.intesys.rookie.service.DoctorService;
import it.intesys.rookie.service.NotFound;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DoctorApi {
    public static final String API_DOCTOR = "/api/doctor";
    public static final String API_DOCTOR_ID = API_DOCTOR + "/{id}";
    private final DoctorService doctorService;

    public DoctorApi(DoctorService doctorService) {
        this.doctorService = doctorService;
    }
    @PostMapping("/api/doctor")
    DoctorDTO createDoctor(@RequestBody DoctorDTO doctor){
        return doctorService.createDoctor (doctor);
    }
    @GetMapping(API_DOCTOR_ID)
    ResponseEntity<DoctorDTO> getDoctor(@PathVariable Long id){
        try{
            DoctorDTO doctor = doctorService.getDoctor(id);
            return ResponseEntity.ok(doctor);
        }catch(NotFound e){
            return ResponseEntity.notFound().header("X-rookie-header",e.getMessage()).build();
        }
    }
    @DeleteMapping(API_DOCTOR_ID)
    ResponseEntity<DoctorDTO> delDoctor(@PathVariable Long id){
        try{
            DoctorDTO doctor = doctorService.delDoctor(id);
            return ResponseEntity.ok(doctor);
        }catch(NotFound e){
            return ResponseEntity.notFound().header("X-rookie-header",e.getMessage()).build();
        }
    }
    @PutMapping(API_DOCTOR_ID)
    DoctorDTO updateDoctor (@PathVariable Long id, @RequestBody DoctorDTO doctorDTO){
        return doctorService.updateDoctor(id, doctorDTO);
    }
}
