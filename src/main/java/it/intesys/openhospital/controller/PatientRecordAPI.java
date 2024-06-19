package it.intesys.openhospital.controller;

import it.intesys.openhospital.dto.PatientRecordDTO;
import it.intesys.openhospital.service.PatientRecordService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientRecordAPI extends CommonApi {
    public static final String API_PATIENT_RECORD = "/api/patientRecord";
    private PatientRecordService patientRecordService;

    public PatientRecordAPI(PatientRecordService patientRecordService) {
        this.patientRecordService = patientRecordService;
    }

    @PostMapping(API_PATIENT_RECORD)
    PatientRecordDTO createPatientRecord(@RequestBody PatientRecordDTO patientRecordDTO){
        return patientRecordService.createPatientRecord(patientRecordDTO);
    }
}

