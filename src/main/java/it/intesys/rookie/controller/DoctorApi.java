package it.intesys.rookie.controller;
import it.intesys.rookie.domain.Doctor;
import it.intesys.rookie.dto.DoctorDTO;
import it.intesys.rookie.dto.DoctorFilterDTO;
import it.intesys.rookie.service.DoctorService;
import it.intesys.rookie.service.NotFound;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DoctorApi extends RookieApi {
    private static final String API_DOCTOR = "/api/doctor";

    private static final String API_DOCTOR_FILTER = API_DOCTOR + "/filter" ;
    private final DoctorService doctorService;

    public DoctorApi(DoctorService doctorService) {
        this.doctorService = doctorService;
    }
    @PostMapping(API_DOCTOR)
    DoctorDTO createDoctor(@RequestBody DoctorDTO doctor){
        return doctorService.createDoctor (doctor);
    }

    @GetMapping(API_DOCTOR + "/{id}")
    DoctorDTO getDoctor(@PathVariable ("id") Long id){
        return doctorService.getDoctor (id);
    }
    @PostMapping(API_DOCTOR_FILTER)
    ResponseEntity<List<DoctorDTO>> getDoctors(@RequestParam("page") int page, @RequestParam("size")int size, @RequestParam("sort")String sort, @RequestBody @Nullable DoctorFilterDTO filter){
        Pageable pageable = pageableOf(page,size,sort);
        Page<DoctorDTO> doctors = doctorService.getDoctors (filter, pageable);

        HttpHeaders httpHeaders = paginationHeaders(doctors, API_DOCTOR_FILTER);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(doctors.getContent());
    }


    @PutMapping(API_DOCTOR + "/{id}")
    void updateDoctor (@PathVariable Long id, @RequestBody DoctorDTO doctor) {
        DoctorDTO doctorDTO = doctorService.updateDoctor(id, doctor);
    }

    @DeleteMapping(API_DOCTOR + "/{id}")
    ResponseEntity<DoctorDTO> deleteDoctor (@PathVariable Long id) {
        try {
            doctorService.deleteDoctor(id);
            return ResponseEntity.ok().build();
        } catch (NotFound e) {
            return ResponseEntity.notFound().header("X-rookie-error", e.getMessage()).build();
        }
    }



}




