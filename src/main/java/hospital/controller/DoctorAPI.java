package hospital.controller;

import hospital.dto.DoctorDTO;
import hospital.dto.DoctorFilterDTO;
import hospital.service.DoctorService;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DoctorAPI extends CommonAPI {
    private final DoctorService doctorService;
    public static final String API_DOCTOR = "/api/doctor";
    public static final String API_DOCTOR_ID = API_DOCTOR + "/{id}";
    public static final String API_DOCTOR_FILTER = API_DOCTOR + "/filter";

    public DoctorAPI(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping(API_DOCTOR)
    DoctorDTO createDoctor(@RequestBody DoctorDTO doctor){
        return doctorService.createDoctor(doctor);
    }
    @GetMapping(API_DOCTOR_ID)
    ResponseEntity<DoctorDTO> getDoctor(@PathVariable Long id){
        DoctorDTO doctor = doctorService.getDoctor(id);
        return ResponseEntity.ok(doctor);
    }
    @PutMapping(API_DOCTOR_ID)
    void updateDoctor(@PathVariable Long id, @RequestBody DoctorDTO doctorDTO){
        doctorService.updateDoctor(id, doctorDTO);
    }

    @PostMapping(API_DOCTOR_FILTER)
    ResponseEntity<List<DoctorDTO>> getDoctors(@RequestParam ("page") int page, @RequestParam ("size") int size, @RequestParam ("sort") String sort, @RequestBody @Nullable DoctorFilterDTO filter){
        Pageable pageable = pageableOf(page, size, sort);
        Page<DoctorDTO> doctors = doctorService.getDoctors(filter, pageable);
        HttpHeaders httpHeaders = paginationHeaders(doctors, API_DOCTOR_FILTER);
        return ResponseEntity.ok().headers(httpHeaders).body(doctors.getContent());
    }
    @DeleteMapping(API_DOCTOR_ID)
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id){
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok().build();
    }
}



