package com.hospital.hms.repository;

import com.hospital.hms.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);

    Page<Patient> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String FirstName, String lastName, Pageable pageable);

}
