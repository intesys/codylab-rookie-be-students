package hospital.controller;


import hospital.dto.PatientDTO;
import hospital.dto.PatientFilterDTO;
import hospital.service.NotFound;
import hospital.service.PatientService;
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
public class PatientAPI extends CommonAPI {

    private static final String API_PATIENT = "/api/patient" ;
    private static final String API_PATIENT_ID = API_PATIENT + "/{id}";
    private static final String API_PATIENT_FILTER = API_PATIENT + "/filter";
    private final PatientService patientService;

    public PatientAPI(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping(API_PATIENT)
    PatientDTO createPatient (@RequestBody PatientDTO patient) {
        return patientService.createPatient (patient);
    }

    @GetMapping(API_PATIENT_ID)
    ResponseEntity<PatientDTO> getPatient (@PathVariable Long id){
        try {
            PatientDTO patient = patientService.getPatient(id);
            return   ResponseEntity.ok(patient);
        }catch (NotFound e) {
            return ResponseEntity.notFound().header("x-rookie-error", e.getMessage()).build();
        }
    }
    @PutMapping
    PatientDTO updatePatient(@PathVariable Long id, @RequestBody PatientDTO patientDTO){
        return patientService.updatePatient(id, patientDTO);
    }
    @DeleteMapping(API_PATIENT_ID)
    ResponseEntity<Void> deletePatient (@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok(null);
    }
    @PostMapping(API_PATIENT_FILTER)
    ResponseEntity<List<PatientDTO>> getPatients (@RequestParam ("page") int page, @RequestParam ("size") int size, @RequestParam ("sort") String sort, @RequestParam PatientFilterDTO filter){
        Pageable pageable = pageableOf(page, size, sort);
        Page<PatientDTO> patients = patientService.getPatients(filter, pageable);
        HttpHeaders httpHeaders = paginationHeaders(patients,API_PATIENT_FILTER);
        return ResponseEntity.ok().headers(httpHeaders).body(patients.getContent());
    }


}

