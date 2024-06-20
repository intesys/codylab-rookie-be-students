package it.intesys.openhospital.controller;

import it.intesys.openhospital.dto.PatientDTO;
import it.intesys.openhospital.dto.PatientFilterDTO;
import it.intesys.openhospital.dto.PatientRecordDTO;
import it.intesys.openhospital.service.PatientRecordService;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PatientRecordAPI extends CommonApi {
    public static final String API_PATIENT_RECORD = "/api/patientRecord";
    public static final String API_PATIENT_RECORD_ID = API_PATIENT_RECORD + "/{id}";
    private PatientRecordService patientRecordService;

    public PatientRecordAPI(PatientRecordService patientRecordService) {
        this.patientRecordService = patientRecordService;
    }

    @PostMapping(API_PATIENT_RECORD)
    PatientRecordDTO createPatientRecord(@RequestBody PatientRecordDTO patientRecordDTO){
        return patientRecordService.createPatientRecord(patientRecordDTO);
    }

    @GetMapping(API_PATIENT_RECORD_ID)
    ResponseEntity<PatientRecordDTO> getPatientRecord(@PathVariable Long id){
        PatientRecordDTO patientRecordDTO = patientRecordService.getPatientRecord(id);
        return ResponseEntity.ok(patientRecordDTO);
    }

    @PutMapping(API_PATIENT_RECORD_ID)
    public PatientRecordDTO updatePatientRecord(@PathVariable Long id, @RequestBody PatientRecordDTO patientRecordDTO){
        return patientRecordService.updatePatientRecord(id, patientRecordDTO);
    }

    @DeleteMapping(API_PATIENT_RECORD_ID)
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id){
        patientRecordService.deleteDoctor(id);
        return ResponseEntity.ok().build();
    }
}

