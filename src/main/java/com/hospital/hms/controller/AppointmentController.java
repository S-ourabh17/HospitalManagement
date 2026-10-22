package com.hospital.hms.controller;


import com.hospital.hms.dtos.Dtos.AppointmentRequest;
import com.hospital.hms.dtos.Dtos.AppointmentResponse;
import com.hospital.hms.enums.AppointmentStatus;
import com.hospital.hms.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {


    private final AppointmentService service;


    @PostMapping("/book-session")
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentResponse createAppointment(@Valid @RequestBody AppointmentRequest request){
        return service.bookAppointment(request);
    }


    @GetMapping("/{id}")
    public AppointmentResponse getAppointmentById(@PathVariable Long id){
        return service.getAppointmentById(id);
    }



}
