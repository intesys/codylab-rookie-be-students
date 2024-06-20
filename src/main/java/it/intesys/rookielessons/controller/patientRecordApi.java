package it.intesys.rookielessons.controller;

import it.intesys.rookielessons.dto.PatientRecordDTO;
import it.intesys.rookielessons.service.PatientRecordService;
import it.intesys.rookielessons.service.NotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class patientRecordApi extends HospitalApi{
    public static final String API_PATIENTRECORD = "/api/patientRecord";
    public static final String API_PATIENTRECORD_FILTER = API_PATIENTRECORD + "/filter";
    private final PatientRecordService patientRecordService;

    public patientRecordApi(PatientRecordService patientRecordService) {
        this.patientRecordService = patientRecordService;
    }

    @PostMapping(API_PATIENTRECORD)
    PatientRecordDTO createPatientRecord (@RequestBody PatientRecordDTO patientRecord) {
        return patientRecordService.createPatientRecord (patientRecord);
    }

    @GetMapping(API_PATIENTRECORD + "/{id}")
    PatientRecordDTO getPatientRecord (@PathVariable Long id) {
        return patientRecordService.getPatientRecord(id);
    }

    @PutMapping(API_PATIENTRECORD + "/{id}")
    PatientRecordDTO updatePatientRecord (@PathVariable Long id, @RequestBody PatientRecordDTO patientRecord) {
        return patientRecordService.updatePatientRecord(id, patientRecord);
    }

    @DeleteMapping(API_PATIENTRECORD + "/{id}")
    ResponseEntity<PatientRecordDTO> deletePatientRecord (@PathVariable Long id) {
        try {
            patientRecordService.deletePatientRecord(id);
            return ResponseEntity.ok().build();
        } catch (NotFound e) {
            return ResponseEntity.notFound().header("X-rookielessons-error", e.getMessage()).build();
        }
    }

    @PostMapping(API_PATIENTRECORD_FILTER)
    ResponseEntity<List<PatientRecordDTO>> getPatientRecords (@RequestParam ("page") int page, @RequestParam ("size") int size, @RequestParam ("sort") String sort, @Nullable @RequestBody String filter) {
        Pageable pageable = pageableOf(page, size, sort);
        Page<PatientRecordDTO> patientRecords = patientRecordService.getPatientRecords (filter, pageable);

        HttpHeaders httpHeaders = paginationHeaders(patientRecords, API_PATIENTRECORD_FILTER);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(patientRecords.getContent());
    }

}
