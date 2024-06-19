package it.intesys.rookielessons.controller;

import it.intesys.rookielessons.domain.Patient;
import it.intesys.rookielessons.dto.PatientDTO;
import it.intesys.rookielessons.service.PatientService;
import it.intesys.rookielessons.service.Mandatory;
import it.intesys.rookielessons.service.NotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class PatientApi extends HospitalApi {
    public static final String API_PATIENT = "/api/patient";
    public static final String API_PATIENT_FILTER = API_PATIENT + "/filter";
    private final PatientService patientService;

    public PatientApi(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping(API_PATIENT)
    PatientDTO createPatient (@RequestBody PatientDTO patient) {
        return patientService.createPatient (patient);
    }

    @GetMapping(API_PATIENT + "/{id}")
    PatientDTO getPatient (@PathVariable Long id) {
        PatientDTO patient = patientService.getPatient(id);
        return patient;
    }

    @PutMapping(API_PATIENT + "/{id}")
    PatientDTO updatePatient (@PathVariable Long id, @RequestBody PatientDTO patient) {
        PatientDTO patientDTO = patientService.updatePatient(id, patient);
        return patientDTO;
    }

    @DeleteMapping(API_PATIENT + "/{id}")
    ResponseEntity<PatientDTO> deletePatient (@PathVariable Long id) {
        try {
            patientService.deletePatient(id);
            return ResponseEntity.ok().build();
        } catch (NotFound e) {
            return ResponseEntity.notFound().header("X-rookielessons-error", e.getMessage()).build();
        }
    }

    @PostMapping(API_PATIENT_FILTER)
    ResponseEntity<List<PatientDTO>> getPatients (@RequestParam ("page") int page, @RequestParam ("size") int size, @RequestParam ("sort") String sort, @Nullable @RequestBody String filter) {
        Pageable pageable = pageableOf(page, size, sort);
        Page<PatientDTO> patients = patientService.getPatients (filter, pageable);

        HttpHeaders httpHeaders = paginationHeaders(patients, API_PATIENT_FILTER);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(patients.getContent());
    }

}
