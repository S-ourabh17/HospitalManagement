package com.hospital.hms.controller;

import com.hospital.hms.dtos.Dtos.AppointmentResponse;
import com.hospital.hms.dtos.Dtos.PatientRequest;
import com.hospital.hms.dtos.Dtos.PatientResponse;
import com.hospital.hms.service.AppointmentService;
import com.hospital.hms.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/patients")
public class PatientController {
    private final PatientService patientService;
    private final AppointmentService appointmentService;

    @PostMapping("/add/patient")
    @ResponseStatus(HttpStatus.CREATED)
    public PatientResponse createPatient(@Valid  @RequestBody PatientRequest request){
        return patientService.createPatient(request);
    }


    @GetMapping("/patient/chunks")
    public Page<PatientResponse> search(@RequestParam(required = false) String name, Pageable pageable){
        return patientService.search(name, pageable);
    }

    @GetMapping("patient/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PatientResponse findPatientById(@PathVariable Long id){
        return patientService.get(id);
    }



    @PutMapping("/{id}")
    public PatientResponse updatePatient(@PathVariable Long id, @Valid @RequestBody PatientRequest request){
        return patientService.update(id, request );
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatient(@PathVariable Long id){

        patientService.deleteById(id);
    }

    @GetMapping("/{id}/appointments")
    public AppointmentResponse appointments(@PathVariable Long id){
        return appointmentService.getAppointmentById(id);
    }

}
