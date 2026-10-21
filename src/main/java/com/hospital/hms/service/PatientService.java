package com.hospital.hms.service;


import com.hospital.hms.dtos.Dtos.PatientRequest;
import com.hospital.hms.dtos.Dtos.PatientResponse;

import com.hospital.hms.entity.Patient;
import com.hospital.hms.exception.BusinessException;
import com.hospital.hms.exception.ResourceNotFoundException;
import com.hospital.hms.repository.PatientRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository repository;


    public PatientResponse createPatient(PatientRequest request) {
        if (request.email() != null && repository.existsByEmailIgnoreCase(request.email())) {
            throw new BusinessException("A patient with this email already exists");
        }

        Patient patient = new Patient();
        apply(patient, request);
        return PatientResponse.from(repository.save(patient));
    }

    public Page<PatientResponse> search(String name, Pageable pageable){
        Page<Patient> page =(name== null || name.isBlank())
                ?repository.findAll(pageable)
                :repository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name, pageable);
        return page.map(PatientResponse::from);

    }

    @Transactional(readOnly = true)
    public PatientResponse get(Long id) {
        return PatientResponse.from(find(id));
    }


    public void delete(Long id){
        repository.deleteById(id);
    }


    Patient find(Long id){
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patient", id));
    }

    public PatientResponse update(Long id, PatientRequest request){
        Patient patient = find(id);
        if(request.email() != null && repository.existsByEmailIgnoreCaseAndIdNot(request.email(), id)){
            throw new BusinessException("A patient with this email already exists");
        }

        apply(patient, request);
        return PatientResponse.from(patient);
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }


    private void apply(Patient patient, PatientRequest r){
        patient.setFirstName(r.firstName());
        patient.setLastName(r.lastName());
        patient.setGender(r.gender());
        patient.setDateOfBirth(r.dateOfBirth());
        patient.setPhone(r.phone());
        patient.setEmail(r.email());
        patient.setAddress(r.address());
        patient.setBloodGroup(r.bloodGroup());

    }


}
