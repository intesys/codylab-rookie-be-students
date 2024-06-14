package it.intesys.rookie.controller;

import it.intesys.rookie.dto.DoctorDTO;
import it.intesys.rookie.dto.PatientDTO;
import it.intesys.rookie.service.NotFound;
import it.intesys.rookie.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PatientApi {
    public static final String API_ACCOUNT = "/api/patient";
    private final PatientService patientService;

    public PatientApi(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping(API_ACCOUNT)
    PatientDTO createPatient (@RequestBody PatientDTO patient) {
        return patientService.createPatient(patient);
    }

    @GetMapping(API_ACCOUNT + "/{id}")
    ResponseEntity<PatientDTO> getPatient (@PathVariable Long id) {
        try {
            PatientDTO patientDTO = patientService.getPatient(id);
            return ResponseEntity.ok(patientDTO);
        } catch (NotFound e){
            return ResponseEntity.notFound().header("X-rookie-error", e.getMessage()).build();
        }
    }

    @PutMapping(API_ACCOUNT + "/{id}")
    PatientDTO updatePatient (@PathVariable Long id, @RequestBody PatientDTO patient) {
        return patientService.updatePatient(id, patient);
    }

    @DeleteMapping(API_ACCOUNT + "/{id}")
    ResponseEntity<PatientDTO> deletePatient (@PathVariable Long id) {
        try {
            PatientDTO patientDTO = patientService.deletePatient(id);
            return ResponseEntity.ok(patientDTO);
        } catch (NotFound e){
            return ResponseEntity.notFound().header("X-rookie-error", e.getMessage()).build();
        }
    }
}
