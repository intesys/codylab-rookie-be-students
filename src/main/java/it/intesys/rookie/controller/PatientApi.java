package it.intesys.rookie.controller;

import it.intesys.rookie.service.NotFound;
import it.intesys.rookie.dto.PatientDTO;
import it.intesys.rookie.service.PatientService;
import jakarta.annotation.Nullable;
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
public class PatientApi {
    public static final String API_PATIENT = "/api/patient";
    public static final String API_PATIENT_ID = API_PATIENT + "/{id}";
    private static final String API_PATIENT_FILTER = API_PATIENT + "/filter";
    private final PatientService patientService;

    public PatientApi(PatientService patientService) {
        this.patientService = patientService;
    }
    @PostMapping("/api/patient")

    PatientDTO createPatient(@RequestBody PatientDTO patient){
        return patientService.createPatient (patient);
    }
    @GetMapping(API_PATIENT_ID)
    ResponseEntity<PatientDTO> getPatient(@PathVariable Long id){
        try{
            PatientDTO patient = patientService.getPatient(id);
            return ResponseEntity.ok(patient);
        }catch(NotFound e){
            return ResponseEntity.notFound().header("X-rookie-header",e.getMessage()).build();
        }
    }
    @DeleteMapping(API_PATIENT_ID)
    ResponseEntity<Void> delPatient(@PathVariable Long id){
        try {
            patientService.delPatient(id);
            return ResponseEntity.ok().build();
        } catch (NotFound e) {
            return ResponseEntity.notFound().header("X-rookie-error", e.getMessage()).build();
        }
    }
    @PutMapping(API_PATIENT_ID)
    PatientDTO updatePatient (@PathVariable Long id, @RequestBody PatientDTO patientDTO){
        return patientService.updatePatient(id, patientDTO);
    }
    @PostMapping(API_PATIENT_FILTER)
    ResponseEntity <List<PatientDTO>> getPatients(@RequestParam ("page") int page, @RequestParam ("size") int size, @RequestParam ("sort") String sort, @RequestBody @Nullable String filter){
        Pageable pageable = pageableOf(page, size, sort);
        Page<PatientDTO> patients = patientService.getPatients(filter, pageable);
        HttpHeaders httpHeaders = paginationHeaders(patients, API_PATIENT_FILTER);

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(patients.getContent());
    }
    protected Pageable pageableOf(Integer page, Integer size, String sort) {
        if (sort != null && !sort.isBlank()) {
            Sort.Order order;
            String[] sortSplit = sort.split(",");
            String valueField = sortSplit[0].trim();

            if (sortSplit.length == 2) {
                String sortingField = sortSplit[1];
                order = new Sort.Order(Sort.Direction.fromString(sortingField.trim()), valueField);
            } else {
                order = Sort.Order.by(valueField);
            }

            return PageRequest.of(page, size, Sort.by(order));
        } else {
            return PageRequest.of(page, size);
        }
    }
    protected <T> HttpHeaders paginationHeaders(Page<T> page, String baseUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", Long.toString(page.getTotalElements()));
        headers.add("X-Page-Number", Long.toString(page.getNumber()));
        headers.add("X-Page-Size", Long.toString(page.getNumberOfElements()));
        String link = "";
        if ((page.getNumber() + 1) < page.getTotalPages()) {
            link = "<" + generateUri(baseUrl, page.getNumber() + 1, page.getSize()) + ">; rel=\"next\",";
        }
        // prev link
        if ((page.getNumber()) > 0) {
            link += "<" + generateUri(baseUrl, page.getNumber() - 1, page.getSize()) + ">; rel=\"prev\",";
        }
        // last and first link
        int lastPage = 0;
        if (page.getTotalPages() > 0) {
            lastPage = page.getTotalPages() - 1;
        }
        link += "<" + generateUri(baseUrl, lastPage, page.getSize()) + ">; rel=\"last\",";
        link += "<" + generateUri(baseUrl, 0, page.getSize()) + ">; rel=\"first\"";
        headers.add(HttpHeaders.LINK, link);
        return headers;
    }

    private static String generateUri(String baseUrl, int page, int size) {
        return UriComponentsBuilder.fromUriString(baseUrl).queryParam("page", page).queryParam("size", size).toUriString();
    }
}
