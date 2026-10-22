package com.hospital.hms.repository;


import com.hospital.hms.entity.Appointment;
import com.hospital.hms.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByDoctorIdAndAppointmentTimeAndStatusNot(Long doctorId, LocalDateTime time, AppointmentStatus status);

    List<Appointment> findByPatientIdOrderByAppointmentTimeDesc(Long patientId);


}
