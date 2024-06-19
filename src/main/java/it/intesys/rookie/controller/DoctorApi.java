package it.intesys.rookie.controller;
import it.intesys.rookie.domain.Doctor;
import it.intesys.rookie.dto.DoctorDTO;
import it.intesys.rookie.dto.PatientDTO;
import it.intesys.rookie.service.DoctorService;
import it.intesys.rookie.service.NotFound;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.print.Doc;
import java.util.List;

@RestController
public class DoctorApi extends RookieApi{
    public static final String API_DOCTOR = "/api/doctor";
    public static final String API_DOCTOR_ID = API_DOCTOR + "/{id}";
    private static final String API_DOCTOR_FILTER = API_DOCTOR + "/filter";
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
    ResponseEntity<Void> delDoctor(@PathVariable Long id){
        try {
            doctorService.delDoctor(id);
            return ResponseEntity.ok().build();
        } catch (NotFound e) {
            return ResponseEntity.notFound().header("X-rookie-error", e.getMessage()).build();
        }
    }
    @PutMapping(API_DOCTOR_ID)
    DoctorDTO updateDoctor (@PathVariable Long id, @RequestBody DoctorDTO doctorDTO){
        return doctorService.updateDoctor(id, doctorDTO);
    }
    @PostMapping(API_DOCTOR_FILTER)
    ResponseEntity <List<DoctorDTO>> getDoctors(@RequestParam ("page") int page, @RequestParam ("size") int size, @RequestParam ("sort") String sort, @RequestBody @Nullable String filter){
        Pageable pageable = pageableOf(page, size, sort);
        Page<DoctorDTO> doctors = doctorService.getDoctors(filter, pageable);
        HttpHeaders httpHeaders = paginationHeaders(doctors, API_DOCTOR_FILTER);

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(doctors.getContent());
    }

}
