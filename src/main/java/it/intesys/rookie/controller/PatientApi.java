package it.intesys.rookie.controller;

import it.intesys.rookie.dto.PatientDTO;
import it.intesys.rookie.service.PatientService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientApi {
    private final PatientService patientService;

    public PatientApi(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/api/patient")
    PatientDTO createPatient (@RequestBody PatientDTO patient) {
        return patientService.createPatient(patient);
    }
}
