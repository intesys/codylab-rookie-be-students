package it.intesys.rookie.controller;

import it.intesys.rookie.dto.PatientFilterDTO;
import it.intesys.rookie.service.NotFound;
import it.intesys.rookie.dto.PatientDTO;
import it.intesys.rookie.service.PatientService;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PatientApi extends RookieApi {
    public static final String API_PATIENT = "/api/patient";
    public static final String API_PATIENT_ID = API_PATIENT + "/{id}";
    private static final String API_PATIENT_FILTER = API_PATIENT + "/filter";
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
    ResponseEntity<Void> delPatient(@PathVariable Long id){
        try {
            patientService.delPatient(id);
            return ResponseEntity.ok().build();
        } catch (NotFound e) {
            return ResponseEntity.notFound().header("X-rookie-error", e.getMessage()).build();
        }
    }
    @PutMapping(API_PATIENT_ID)
    void updatePatient (@PathVariable Long id, @RequestBody PatientDTO patientDTO){
        patientService.updatePatient(id, patientDTO);
    }
    @PostMapping(API_PATIENT_FILTER)
    ResponseEntity <List<PatientDTO>> getPatients(@RequestParam ("page") int page, @RequestParam ("size") int size, @RequestParam ("sort") String sort, @RequestBody @Nullable PatientFilterDTO filter){
        Pageable pageable = pageableOf(page, size, sort);
        Page<PatientDTO> patients = patientService.getPatients(filter, pageable);
        HttpHeaders httpHeaders = paginationHeaders(patients, API_PATIENT_FILTER);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(patients.getContent());
    }

}
