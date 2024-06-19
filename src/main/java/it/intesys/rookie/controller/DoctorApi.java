package it.intesys.rookie.controller;

import it.intesys.rookie.dto.DoctorFilterDTO;
import it.intesys.rookie.utilities.Utilities;
import it.intesys.rookie.dto.DoctorDTO;
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
public class DoctorApi extends Utilities {
    public static final String API_ACCOUNT = "/api/doctor";
    private final DoctorService doctorService;

    public DoctorApi(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping("/api/doctor")
    DoctorDTO createDoctor (@RequestBody DoctorDTO doctor) {
        return doctorService.createDoctor(doctor);
    }

    @GetMapping(API_ACCOUNT + "/{id}")
    ResponseEntity<DoctorDTO> getDoctor (@PathVariable Long id) {
        try {
            DoctorDTO doctorDTO = doctorService.getDoctor(id);
            return ResponseEntity.ok(doctorDTO);
        } catch (NotFound e){
            return ResponseEntity.notFound().header("X-rookie-error", e.getMessage()).build();
        }
    }

    @PutMapping(API_ACCOUNT + "/{id}")
    DoctorDTO updateDoctor (@PathVariable Long id, @RequestBody DoctorDTO doctor) {
        return doctorService.updateDoctor(id, doctor);
    }

    @DeleteMapping(API_ACCOUNT + "/{id}")
    ResponseEntity<DoctorDTO> deleteDoctor (@PathVariable Long id) {
        try {
            doctorService.deleteDoctor(id);
            return ResponseEntity.ok().build();
        } catch (NotFound e){
            return ResponseEntity.notFound().header("X-rookie-error", e.getMessage()).build();
        }
    }

    @PostMapping(API_ACCOUNT + "/filter")
    ResponseEntity<List<DoctorDTO>> getDoctors (@RequestParam ("page") int page, @RequestParam ("size") int size, @RequestParam ("sort") String sort, @Nullable @RequestBody DoctorFilterDTO filter){
        Pageable pageable = pageableOf(page, size, sort);
        Page<DoctorDTO> doctors = doctorService.getDoctors(filter, pageable);

        HttpHeaders httpHeaders = Utilities.paginationHeaders(doctors, API_ACCOUNT + "/filter");
        return ResponseEntity.ok().headers(httpHeaders).body(doctors.getContent());
    }

}
