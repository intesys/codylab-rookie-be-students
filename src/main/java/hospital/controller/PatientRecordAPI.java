package hospital.controller;

import hospital.dto.PatientRecordDTO;
import hospital.service.PatientRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PatientRecordAPI {
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
