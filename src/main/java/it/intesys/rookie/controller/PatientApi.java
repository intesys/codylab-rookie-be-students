package it.intesys.rookie.controller;

import it.intesys.rookie.dto.DoctorDTO;
import it.intesys.rookie.dto.DoctorFilterDTO;
import it.intesys.rookie.dto.PatientDTO;
import it.intesys.rookie.dto.PatientFilterDTO;
import it.intesys.rookie.service.NotFound;
import it.intesys.rookie.service.PatientService;
import it.intesys.rookie.utilities.Utilities;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PatientApi extends Utilities{
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

    @PostMapping(API_ACCOUNT + "/filter")
    ResponseEntity<List<PatientDTO>> getPatient (@RequestParam ("page") int page, @RequestParam ("size") int size, @RequestParam ("sort") String sort, @Nullable @RequestBody PatientFilterDTO filter){
        Pageable pageable = pageableOf(page, size, sort);
        Page<PatientDTO> patient = patientService.getPatients(filter, pageable);

        HttpHeaders httpHeaders = Utilities.paginationHeaders(patient, API_ACCOUNT + "/filter");
        return ResponseEntity.ok().headers(httpHeaders).body(patient.getContent());
    }
}
