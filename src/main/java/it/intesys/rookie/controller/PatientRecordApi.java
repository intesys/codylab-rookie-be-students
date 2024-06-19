package it.intesys.rookie.controller;

import it.intesys.rookie.dto.PatientRecordDTO;
import it.intesys.rookie.service.NotFound;
import it.intesys.rookie.service.PatientRecordService;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PatientRecordApi extends RookieApi{
    public static final String API_PATIENTRECORD = "/api/patientRecord";
    public static final String API_PATIENTRECORD_ID = API_PATIENTRECORD + "/{id}";
    private static final String API_PATIENTRECORD_FILTER = API_PATIENTRECORD + "/filter";
    private final PatientRecordService patientRecordService;

    public PatientRecordApi(PatientRecordService patientRecordService) {
        this.patientRecordService = patientRecordService;
    }
    @PostMapping(API_PATIENTRECORD)
    PatientRecordDTO createPatientRecord(@RequestBody PatientRecordDTO patientRecord){
        return patientRecordService.createPatientRecord (patientRecord);
    }
    @GetMapping(API_PATIENTRECORD_ID)
    ResponseEntity<PatientRecordDTO> getPatientRecord(@PathVariable Long id){
        try{
            PatientRecordDTO patientRecord = patientRecordService.getPatientRecord(id);
            return ResponseEntity.ok(patientRecord);
        }catch(NotFound e){
            return ResponseEntity.notFound().header("X-rookie-header",e.getMessage()).build();
        }
    }
    @DeleteMapping(API_PATIENTRECORD_ID)
    ResponseEntity<Void> delPatientRecord(@PathVariable Long id){
        try {
            patientRecordService.delPatientRecord(id);
            return ResponseEntity.ok().build();
        } catch (NotFound e) {
            return ResponseEntity.notFound().header("X-rookie-error", e.getMessage()).build();
        }
    }
    @PutMapping(API_PATIENTRECORD_ID)
    PatientRecordDTO updatePatientRecord (@PathVariable Long id, @RequestBody PatientRecordDTO patientRecordDTO){
        return patientRecordService.updatePatientRecord(id, patientRecordDTO);
    }
    @PostMapping(API_PATIENTRECORD_FILTER)
    ResponseEntity <List<PatientRecordDTO>> getPatientRecords(@RequestParam ("page") int page, @RequestParam ("size") int size, @RequestParam ("sort") String sort, @RequestBody @Nullable String filter){
        Pageable pageable = pageableOf(page, size, sort);
        Page<PatientRecordDTO> patientRecords = patientRecordService.getPatientRecords(filter, pageable);
        HttpHeaders httpHeaders = paginationHeaders(patientRecords, API_PATIENTRECORD_FILTER);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(patientRecords.getContent());
    }
}
