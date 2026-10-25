package com.hospital.hms.repository;

import com.hospital.hms.dtos.Dtos;
import com.hospital.hms.entity.Doctor;
import com.hospital.hms.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord,Long> {
    List<MedicalRecord> findByPatientIdOrderByCreatedAtDesc(Long id);
}
