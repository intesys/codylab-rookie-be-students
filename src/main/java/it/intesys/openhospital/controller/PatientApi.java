package it.intesys.openhospital.controller;

import it.intesys.openhospital.dto.PatientDTO;
import it.intesys.openhospital.dto.PatientFilterDTO;
import it.intesys.openhospital.service.PatientService;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PatientApi extends CommonApi {
    private final PatientService patientService;
    public static final String API_PATIENT = "/api/patient";
    public static final String API_PATIENT_ID = API_PATIENT + "/{id}";
    public static final String API_PATIENT_FILTER = API_PATIENT + "/filter";

    public PatientApi(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping(API_PATIENT)
    PatientDTO createPatient(@RequestBody PatientDTO patient){
        return patientService.createPatient(patient);
    }
    @GetMapping(API_PATIENT_ID)
    ResponseEntity<PatientDTO> getPatient(@PathVariable Long id){
            PatientDTO patient = patientService.getPatient(id);
            return ResponseEntity.ok(patient);
    }
    @PutMapping(API_PATIENT_ID)
    public PatientDTO updatePatient(@PathVariable Long id, @RequestBody PatientDTO patientDTO){
        return patientService.updatePatient(id, patientDTO);
    }

    @PostMapping(API_PATIENT_FILTER)
    ResponseEntity<List<PatientDTO>> getPatients(@RequestParam ("page") int page, @RequestParam ("size") int size, @RequestParam ("sort") String sort, @RequestBody @Nullable PatientFilterDTO filter){
        Pageable pageable = pageableOf(page, size, sort);
        Page<PatientDTO> patients = patientService.getPatients(filter, pageable);
        HttpHeaders httpHeaders = paginationHeaders(patients, API_PATIENT_FILTER);
        return ResponseEntity.ok().headers(httpHeaders).body(patients.getContent());
    }
    @DeleteMapping(API_PATIENT_ID)
    public ResponseEntity<Void> deletePatient(@PathVariable Long id){
            patientService.deletePatient(id);
            return ResponseEntity.ok().build();
    }

}
