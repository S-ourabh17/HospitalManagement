package com.hospital.hms.service;


import com.hospital.hms.dtos.Dtos.BillResponse;
import com.hospital.hms.entity.Appointment;
import com.hospital.hms.entity.Bill;
import com.hospital.hms.enums.AppointmentStatus;
import com.hospital.hms.enums.BillStatus;
import com.hospital.hms.exception.BusinessException;
import com.hospital.hms.exception.ResourceNotFoundException;
import com.hospital.hms.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class BillService {
    private final BillRepository billRepository;
    private final PatientService patientService;
    private final AppointmentService appointmentService;

    public BillResponse generateForAppointment(Long appointmentId){
        Appointment app = appointmentService.find(appointmentId);

        if (app.getStatus() != AppointmentStatus.COMPLETED) {
            throw new BusinessException("Bills can only be generated for COMPLETED appointments");
        }
        if (billRepository.existsByAppointmentId(appointmentId)) {
            throw new BusinessException("A bill already exists for this appointment");
        }

        Bill bill = Bill.builder()
                .patient(app.getPatient())
                .appointment(app)
                .amount(app.getDoctor().getConsultationFee())
                .description("Consultation with Dr. " + app.getDoctor().getFirstName() + " " + app.getDoctor().getLastName())
                .build();
        return BillResponse.from(billRepository.save(bill));
    }

    @Transactional(readOnly = true)
    public BillResponse getById(Long id){
        return BillResponse.from(find(id));
    }

    public List<BillResponse> getAllBillOfPatients(Long patientId){
        patientService.find(patientId);
        return billRepository.findByPatientIdOrderByIssuedAtDesc(patientId)
                .stream().map(BillResponse::from).toList();
    }


    public BillResponse pay(Long id){
        Bill bill = find(id);
        if(bill.getStatus() != BillStatus.PENDING)
            throw new BusinessException("Only PENDING bills can be paid (currnet: " + bill.getStatus() + ")" );
        bill.setStatus(BillStatus.PAID);
        bill.setPaidAt(LocalDateTime.now());
        return BillResponse.from(bill);
    }

    public BillResponse cancel(Long id) {
        Bill bill = find(id);
        if (bill.getStatus() != BillStatus.PENDING) {
            throw new BusinessException("Only PENDING bills can be cancelled (current: " + bill.getStatus() + ")");
        }
        bill.setStatus(BillStatus.CANCELLED);
        return BillResponse.from(bill);
    }


    Bill find(Long id){
        return billRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Bill", id));
    }










}
