package com.hospital.hms.repository;

import com.hospital.hms.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {
  boolean existsByEmailIgnoreCase(String email);
  List<Doctor> findBySpecializationIgnoreCaseAndActiveTrue(String specialization);

  boolean existByEmailIgnoreCaseAndIdNot(String email, Long id);

  List<Doctor> findByActiveTrue();
}
