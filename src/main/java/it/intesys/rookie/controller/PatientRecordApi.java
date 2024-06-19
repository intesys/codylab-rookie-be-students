package it.intesys.rookie.controller;

import it.intesys.rookie.dto.PatientRecordDTO;
import it.intesys.rookie.service.NotFound;
import it.intesys.rookie.service.PatientRecordService;
import it.intesys.rookie.utilities.Utilities;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PatientRecordApi extends Utilities{
    public static final String API_ACCOUNT = "/api/patientRecord";
    private final PatientRecordService patientRecordService;

    public PatientRecordApi(PatientRecordService patientRecordService) {
        this.patientRecordService = patientRecordService;
    }


    @PostMapping(API_ACCOUNT)
    PatientRecordDTO createPatientRecord (@RequestBody PatientRecordDTO patientRecord) {
        return patientRecordService.createPatientRecord(patientRecord);
    }

    @GetMapping(API_ACCOUNT + "/{id}")
    ResponseEntity<PatientRecordDTO> getPatientRecord (@PathVariable Long id) {
        try {
            PatientRecordDTO patientRecordDTO = patientRecordService.getPatientRecord(id);
            return ResponseEntity.ok(patientRecordDTO);
        } catch (NotFound e){
            return ResponseEntity.notFound().header("X-rookie-error", e.getMessage()).build();
        }
    }

    @PutMapping(API_ACCOUNT + "/{id}")
    PatientRecordDTO updatePatientRecordRecord (@PathVariable Long id, @RequestBody PatientRecordDTO patientRecord) {
        return patientRecordService.updatePatientRecord(id, patientRecord);
    }

    @DeleteMapping(API_ACCOUNT + "/{id}")
    ResponseEntity<PatientRecordDTO> deletePatientRecord (@PathVariable Long id) {
        try {
            PatientRecordDTO patientRecordDTO = patientRecordService.deletePatientRecord(id);
            return ResponseEntity.ok(patientRecordDTO);
        } catch (NotFound e){
            return ResponseEntity.notFound().header("X-rookie-error", e.getMessage()).build();
        }
    }
}
