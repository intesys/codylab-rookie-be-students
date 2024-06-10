package it.intesys.rookie.controller;
import it.intesys.rookie.dto.DoctorDTO;
import it.intesys.rookie.dto.DoctorFilterDTO;
import it.intesys.rookie.service.DoctorService;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DoctorApi extends RookieApi {
    private final DoctorService doctorService;

    public DoctorApi(DoctorService doctorService) {
        this.doctorService = doctorService;
    }
    @PostMapping("/api/doctor")
    DoctorDTO createDoctor(@RequestBody DoctorDTO doctor){
        return doctorService.createDoctor (doctor);
    }
    @PostMapping("/api/doctor/filter")
    List<DoctorDTO> getDoctors(@RequestParam("page") int page, @RequestParam("size")int size, @RequestParam("sort")String sort, @RequestBody @Nullable DoctorFilterDTO filter){
        Pageable pageable = pageableOf(page,size,sort);
        Page<DoctorDTO> doctors = doctorService.getDoctors (filter, pageable);



    }

}