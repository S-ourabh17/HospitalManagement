package com.hospital.hms.controller;

import com.hospital.hms.dtos.Dtos;
import com.hospital.hms.dtos.Dtos.MedicalRecordRequest;
import com.hospital.hms.dtos.Dtos.MedicalRecordResponse;
import com.hospital.hms.entity.MedicalRecord;
import com.hospital.hms.repository.MedicalRecordRepository;
import com.hospital.hms.service.MedicalRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medical/record")
public class MedicalRecordController {
    private final MedicalRecordService service;
    private final MedicalRecordRepository repo;


    @PostMapping()
    public MedicalRecordResponse create(@Valid @RequestBody MedicalRecordRequest medicalRecordRequest){
        return service.create(medicalRecordRequest);
    }

    @GetMapping("{id}")
    public MedicalRecordResponse getById(@PathVariable Long id){
        return service.get(id);
    }



}
