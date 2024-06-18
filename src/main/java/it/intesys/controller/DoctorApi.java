package it.intesys.controller;

import it.intesys.dto.DoctorDTO;
import it.intesys.service.DoctorService;
import it.intesys.service.NotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class DoctorApi extends HospitalApi {

    public static final String API_PATIENT = "/api/chat";
    public static final String API_PATIENT_FILTER = API_PATIENT + "/filter";
    private final DoctorService doctorService;

    public DoctorApi(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping(API_PATIENT)
    DoctorDTO createDoctor (@RequestBody DoctorDTO doctor) {
        return doctorService.createDoctor (doctor);
    }

    @GetMapping(API_PATIENT + "/{id}")
    DoctorDTO getDoctor (@PathVariable Long id) {
        DoctorDTO doctor = doctorService.getDoctor(id);
        return doctor;
    }

    @PutMapping(API_PATIENT + "/{id}")
    DoctorDTO updateDoctor (@PathVariable Long id, @RequestBody DoctorDTO doctor) {
        DoctorDTO doctorDTO = doctorService.updateDoctor(id, doctor);
        return doctorDTO;
    }

    @DeleteMapping(API_PATIENT + "/{id}")
    ResponseEntity<DoctorDTO> deleteDoctor (@PathVariable Long id) {
        try {
            doctorService.deleteDoctor(id);
            return ResponseEntity.ok().build();
        } catch (NotFound e) {
            return ResponseEntity.notFound().header("X-rookie-error", e.getMessage()).build();
        }
    }

    @PostMapping(API_PATIENT_FILTER)
    ResponseEntity<List<DoctorDTO>> getDoctors (@RequestParam ("page") int page, @RequestParam ("size") int size, @RequestParam ("sort") String sort, @Nullable @RequestBody String filter) {
        Pageable pageable = pageableOf(page, size, sort);
        Page<DoctorDTO> doctors = doctorService.getDoctors (filter, pageable);

        HttpHeaders httpHeaders = paginationHeaders(doctors, API_PATIENT_FILTER);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(doctors.getContent());
    }
}
