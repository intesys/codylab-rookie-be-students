package it.intesys.rookie.controller;

import it.intesys.rookie.dto.DoctorDTO;
import it.intesys.rookie.service.DoctorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DoctorApi {
    private final DoctorService doctorService;

    public DoctorApi(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping("/api/doctor")
    DoctorDTO createDoctor (@RequestBody DoctorDTO doctor) {
        return doctorService.createDoctor(doctor);
    }
}
