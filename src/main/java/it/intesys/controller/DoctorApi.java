package it.intesys.controller;

import it.intesys.dto.DoctorDTO;
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
    private final DoctorService chatService;

    public DoctorApi(DoctorService doctorService) {
        this.chatService = doctorService;
    }

    @PostMapping(API_PATIENT)
    DoctorDTO createDoctor (@RequestBody DoctorDTO doctor) {
        return chatService.createDoctor (doctor);
    }

    @GetMapping(API_PATIENT + "/{id}")
    DoctorDTO getDoctor (@PathVariable Long id) {
        DoctorDTO doctor = chatService.getDoctor(id);
        return doctor;
    }

    @PutMapping(API_PATIENT + "/{id}")
    DoctorDTO updateDoctor (@PathVariable Long id, @RequestBody DoctorDTO doctor) {
        DoctorDTO doctorDTO = chatService.updateDoctor(id, doctor);
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
