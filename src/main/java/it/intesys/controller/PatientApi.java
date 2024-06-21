package it.intesys.controller;

import it.intesys.dto.PatientDTO;
import it.intesys.service.NotFound;
import it.intesys.service.PatientService;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class PatientApi extends HospitalApi {

    public static final String API_PATIENT = "/api/patient";
    public static final String API_PATIENT_ID = API_PATIENT + "/{id}";

    public static final String API_PATIENT_FILTER = API_PATIENT + "/filter";
    private final PatientService patientservice;

    public PatientApi(PatientService patientservice) {
        this.patientservice = patientservice;
    }

    @PostMapping("/api/patient")
    public PatientDTO createPatient (@RequestBody PatientDTO patient){
        return patientservice.createPatient (patient);
    }


// codice 13/06/2024,

    @GetMapping("/api/patient/{id}")
    ResponseEntity<PatientDTO> getPatient (@PathVariable Long id) {
        try{
            PatientDTO account = patientservice.getPatient(id);
            return ResponseEntity.ok(account);
        } catch (it.intesys.service.NotFound e) {
            return ResponseEntity.notFound().header("x-rookie-error", e.getMessage()).build();
        }
    }

// codice pomeriggio 14/06/2024

    @PutMapping (API_PATIENT_ID)
    void updatePatient (@PathVariable Long id, @RequestBody PatientDTO patientdto){
        patientservice.updatePatient (id, patientdto);
    }

    @DeleteMapping (API_PATIENT_ID)
    ResponseEntity<Void> deletePatient (@PathVariable Long id) {
        try{
            patientservice.deletePatient(id);
            return ResponseEntity.ok().build();
        } catch (NotFound e) {
            return ResponseEntity.notFound().header("x-rookie-error", e.getMessage()).build();
        }
    }

    @PostMapping(API_PATIENT_FILTER)
    ResponseEntity<List<PatientDTO>> getPatient (@RequestParam ("page") int page, @RequestParam ("size") int size, @RequestParam
            ("sort") String sort, @Nullable @RequestBody String filter){
        Pageable pageable = pageableOf(page, size, sort);
        Page<PatientDTO> patients = patientservice.getPatients(filter, pageable);

        HttpHeaders httpheaders = paginationHeaders(patients, API_PATIENT_FILTER);
        return ResponseEntity.ok()
                .headers(httpheaders)
                .body(patients.getContent());
    }
}
