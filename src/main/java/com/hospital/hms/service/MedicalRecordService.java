package com.hospital.hms.service;

import com.hospital.hms.dtos.Dtos.MedicalRecordRequest;
import com.hospital.hms.dtos.Dtos.MedicalRecordResponse;
import com.hospital.hms.entity.Appointment;
import com.hospital.hms.entity.MedicalRecord;
import com.hospital.hms.exception.BusinessException;
import com.hospital.hms.exception.ResourceNotFoundException;
import com.hospital.hms.repository.MedicalRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MedicalRecordService {
    private final AppointmentService appService;
    private final PatientService patients;
    private final DoctorService doctors;
    private final MedicalRecordRepository repo;



    public MedicalRecordResponse create(MedicalRecordRequest req){
        Appointment appointment = null;
        if(req.appointmentId() != null) {
            appointment = appService.find(req.appointmentId());
            if (!appointment.getPatient().getId().equals(req.patientId()) || !appointment.getDoctor().getId().equals(req.doctorId())) {
                throw new BusinessException("Appointment does not belong to the given patient and doctor");
            }
        }
        MedicalRecord record  = MedicalRecord.builder()
                .patient(patients.find(req.patientId()))
                .doctor(doctors.find(req.doctorId()))
                .appointment(appointment)
                .diagnosis(req.diagnosis())
                .prescription(req.prescription())
                .notes(req.notes())
                .build();

        return MedicalRecordResponse.from(repo.save(record));

    }

    @Transactional(readOnly = true)
    public MedicalRecordResponse get(Long id) {
        return MedicalRecordResponse.from(repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medical record", id)));
    }

    @Transactional(readOnly = true)
    public List<MedicalRecordResponse> forPatient(Long id){
        patients.find(id);
        return repo.findByPatientIdOrderByCreatedAtDesc(id)
                .stream().map(MedicalRecordResponse::from).toList();

    }
}
