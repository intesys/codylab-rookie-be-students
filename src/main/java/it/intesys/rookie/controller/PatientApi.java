package it.intesys.rookie.controller;

import it.intesys.rookie.service.NotFound;
import it.intesys.rookie.dto.PatientDTO;
import it.intesys.rookie.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PatientApi {
    public static final String API_PATIENT = "/api/patient";
    public static final String API_PATIENT_ID = API_PATIENT + "/{id}";
    private final PatientService patientService;

    public PatientApi(PatientService patientService) {
        this.patientService = patientService;
    }
    @PostMapping("/api/patient")

    PatientDTO createPatient(@RequestBody PatientDTO patient){
        return patientService.createPatient (patient);
    }
    @GetMapping(API_PATIENT_ID)
    ResponseEntity<PatientDTO> getPatient(@PathVariable Long id){
        try{
            PatientDTO patient = patientService.getPatient(id);
            return ResponseEntity.ok(patient);
        }catch(NotFound e){
            return ResponseEntity.notFound().header("X-rookie-header",e.getMessage()).build();
        }
    }
    @DeleteMapping(API_PATIENT_ID)
    ResponseEntity<PatientDTO> delPatient(@PathVariable Long id){
        try{
            PatientDTO patient = patientService.delPatient(id);
            return ResponseEntity.ok(patient);
        }catch(NotFound e){
            return ResponseEntity.notFound().header("X-rookie-header",e.getMessage()).build();
        }
    }
    @PutMapping(API_PATIENT_ID)
    PatientDTO updatePatient (@PathVariable Long id, @RequestBody PatientDTO patientDTO){
        return patientService.updatePatient(id, patientDTO);
    }
}
