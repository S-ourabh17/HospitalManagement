package com.hospital.hms.service;

import com.hospital.hms.dtos.Dtos.DoctorRequest;
import com.hospital.hms.dtos.Dtos.DoctorResponse;
import com.hospital.hms.entity.Doctor;
import com.hospital.hms.exception.BusinessException;
import com.hospital.hms.exception.ResourceNotFoundException;
import com.hospital.hms.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorResponse createDoctor(DoctorRequest request) {
        if (doctorRepository.existsByEmailIgnoreCase(request.email())) {
            throw new BusinessException("A doctor with this email already exists");
        }

        Doctor doctor = new Doctor();
        apply(doctor, request);
        return DoctorResponse.from(doctorRepository.save(doctor));

    }



    @Transactional(readOnly = true)
    public DoctorResponse get(Long id){

        return DoctorResponse.from(find(id));
    }


    @Transactional(readOnly = true)
    public List<DoctorResponse> doctorList(String specialization){
       List<Doctor> Doctors =  (specialization== null || specialization.isBlank())
               ? doctorRepository.findByActiveTrue() : doctorRepository.findBySpecializationIgnoreCaseAndActiveTrue(specialization);
       return Doctors.stream().map(DoctorResponse::from).toList();
    }

    public DoctorResponse updateDoctor(Long id, DoctorRequest req) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor",id));

        doctor.setFirstName(req.firstName());
        doctor.setLastName(req.lastName());

        doctor.setFirstName(req.firstName());
        doctor.setLastName(req.lastName());
        doctor.setSpecialization(req.specialization());
        doctor.setPhone(req.phone());
        doctor.setEmail(req.email());
        doctor.setConsultationFee(req.consultationFee());
        doctor.setActive(req.active());


        return DoctorResponse.from(doctorRepository.save(doctor));

    }





    Doctor find(Long id){
        return doctorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Doctor", id));
    }

    // Set Doctor deactivate instead of deleting so history stay intact.
    public void deactivate(Long id){
        find(id).setActive(false);
    }

    public void apply(Doctor doctor, DoctorRequest request){
        doctor.setFirstName(request.firstName());
        doctor.setLastName(request.lastName());
        doctor.setSpecialization(request.specialization());
        doctor.setPhone(request.phone());
        doctor.setEmail(request.email());
        doctor.setConsultationFee(request.consultationFee());
        doctor.setActive(request.active());
    }
}
