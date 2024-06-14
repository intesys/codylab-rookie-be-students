package it.intesys.rookie.controller;

import it.intesys.rookie.dto.DoctorDTO;
import it.intesys.rookie.service.DoctorService;
import it.intesys.rookie.service.NotFound;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DoctorApi {
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
            DoctorDTO doctorDTO = doctorService.getAccount(id);
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
            DoctorDTO doctorDTO = doctorService.deleteDoctor(id);
            return ResponseEntity.ok(doctorDTO);
        } catch (NotFound e){
            return ResponseEntity.notFound().header("X-rookie-error", e.getMessage()).build();
        }
    }
}
