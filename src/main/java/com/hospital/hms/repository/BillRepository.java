package com.hospital.hms.repository;

import com.hospital.hms.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill,Long> {
    boolean existsByAppointmentId(Long appointmentId);
    List<Bill> findByPatientIdOrderByIssuedAtDesc(Long patientId);
}
