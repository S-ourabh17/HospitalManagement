package com.hospital.hms.controller;

import com.hospital.hms.dtos.Dtos.DoctorResponse;
import com.hospital.hms.dtos.Dtos.DoctorRequest;
import com.hospital.hms.service.AppointmentService;
import com.hospital.hms.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/doctors")
public class DoctorController {
    private final DoctorService service;
    private final AppointmentService appointments;


    @PostMapping("/add/specialist")
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorResponse createDoctor(@Valid @RequestBody DoctorRequest request){
        return service.createDoctor(request);
    }

    @GetMapping("/doctor/{id}")
    public DoctorResponse getDoctor(@PathVariable Long id){
        return service.get(id);
    }


    @GetMapping("/all/specialist")
    public List<DoctorResponse> getAllDoctorsBySpecialization(@RequestParam String specialization){
        return service.doctorList(specialization);

    }

   @PutMapping("/{id}/update")
    public DoctorResponse update(@PathVariable Long id, @Valid @RequestBody DoctorRequest req) {
        return service.update(id, req);
    }*/


}
