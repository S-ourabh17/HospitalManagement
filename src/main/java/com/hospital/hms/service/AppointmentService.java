package com.hospital.hms.service;
import com.hospital.hms.dtos.Dtos.AppointmentResponse;
import com.hospital.hms.dtos.Dtos.AppointmentRequest;
import com.hospital.hms.entity.Appointment;
import com.hospital.hms.entity.Doctor;
import com.hospital.hms.entity.Patient;
import com.hospital.hms.enums.AppointmentStatus;
import com.hospital.hms.exception.BusinessException;
import com.hospital.hms.exception.ResourceNotFoundException;
import com.hospital.hms.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;                           // Constructor Injection
    private final PatientService patientService;
    private final DoctorService doctorService;


    public AppointmentResponse bookAppointment(AppointmentRequest request){
        Doctor doctor = doctorService.find(request.doctorId());
        Patient patient = patientService.find(request.patientId());

        if(!doctor.isActive()){
            throw new BusinessException("Doctor is not currently  available for appointment");
        }

        if(appointmentRepository.existsByDoctorIdAndAppointmentTimeAndStatusNot(doctor.getId(), request.appointmentTime(), AppointmentStatus.CANCELLED)){
            throw new BusinessException("Doctor already has an appointment at that time");
        }

        Appointment a = Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .appointmentTime(request.appointmentTime())
                .reason(request.reason())
                .build();

        return AppointmentResponse.from(appointmentRepository.save(a));
    }

    @Transactional(readOnly = true)
    public AppointmentResponse getAppointmentById(Long id){
        return AppointmentResponse.from(find(id));
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> forPatient(Long patientId) {
        patientService.find(patientId);
        return appointmentRepository.findByPatientIdOrderByAppointmentTimeDesc(patientId)
                .stream().map(AppointmentResponse::from).toList();
    }

    public AppointmentResponse updateAppointmentStatus(Long id, AppointmentStatus newStatus) {
        Appointment a = find(id);
        if(a.getStatus() !=  AppointmentStatus.SCHEDULED){
            throw  new BusinessException("Only SCHEDULED appointments can be changed (current: " + a.getStatus() + ")");
        }

        if(newStatus == AppointmentStatus.SCHEDULED){
            throw new BusinessException("Appointment is already scheduled");
        }
        a.setStatus(newStatus);
        return AppointmentResponse.from(a);
    }



    @Transactional(readOnly = true)
    Appointment find(Long id){
        return appointmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Appointment", id));
    }

}
