package com.hospital.hms.dtos;

import com.hospital.hms.entity.Appointment;
import com.hospital.hms.entity.Doctor;
import com.hospital.hms.enums.AppointmentStatus;
import com.hospital.hms.enums.Gender;
import com.hospital.hms.entity.Patient;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public final class Dtos {
    private Dtos(){
    }
// ------------------------Patient ----------------------------------
    public record PatientRequest(
            @NotBlank @Size(max = 60) String firstName,
            @NotBlank @Size(max= 60) String lastName,
            @NotBlank Gender gender,
            @NotNull @Past LocalDate dateOfBirth,
            @NotBlank @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number format. Must match E.164 standard (e.g., +1234567890).") String phone,
            @Email String email,
            @Size(max = 225) String address,
            @Pattern(regexp = "^(A|B|AB|O)[+-]$", message = "must be like A+, O-, AB+") String bloodGroup){
    }

    public  record PatientResponse(Long id, String firstName, String lastName, Gender gender,
                                   LocalDate dateOfBirth, String phone, String email, String address,
                                   String bloodGroup, LocalDateTime createdAt){
        public static PatientResponse from(Patient patient){
            return new PatientResponse(patient.getId(),
                                       patient.getFirstName(),
                                       patient.getLastName(),
                                       patient.getGender(),
                    patient.getDateOfBirth(),
                    patient.getPhone(),
                    patient.getEmail(),
                    patient.getAddress(),
                    patient.getBloodGroup(),
                    patient.getCreatedAt());
        }
    }

    // ------------------------------Doctor -----------------------------------

    public record DoctorRequest(
           @NotBlank @Size(max = 60) String firstName,
           @NotBlank @Size(max = 60) String lastName,
           @NotBlank @Size(max = 100) String specialization,
           @NotBlank @Pattern(regexp = "^[0-9+\\- ]{7,15}$", message = "must be a valid phone number") String phone,
           @NotBlank @Email String email,
           @NotNull @DecimalMin("0.0") BigDecimal consultationFee,
           boolean active){

    }

    public record DoctorResponse(Long id, String firstName, String lastName, String specialization,
                                 String phone, String email, BigDecimal consultationFee, boolean active){
        public static DoctorResponse from(Doctor doctor){
            return new DoctorResponse(doctor.getId(),
                    doctor.getFirstName(),
                    doctor.getLastName(),
                    doctor.getSpecialization(),
                    doctor.getPhone(),
                    doctor.getEmail(),
                    doctor.getConsultationFee(),
                    doctor.isActive());
        }
    }


    // -------------------------------Appointment---------------------------
    public record AppointmentRequest(
            @NotNull Long patientId,
            @NotNull Long doctorId,
            @NotNull @Future LocalDateTime appointmentTime,
            @Size(max = 256) String reason
    ){}

    public record AppointmentStatusRequest(@NotNull AppointmentStatus appointmentStatus){
    }

    public record AppointmentResponse(Long id, Long patientId, String patientName, Long doctorId, String doctorName, LocalDateTime appointmentTime, AppointmentStatus status, String reason ){
        public static AppointmentResponse from(Appointment appointment){
            Patient p  = appointment.getPatient();
            Doctor d = appointment.getDoctor();
            return new AppointmentResponse(
                    appointment.getId(),
                    p.getId(),
                    p.getFirstName() + " " + p.getLastName(),
                    d.getId(),
                    "Dr. " + d.getFirstName() + " " + d.getLastName(),
                    appointment.getAppointmentTime(),
                    appointment.getStatus(),
                    appointment.getReason());
        }
    }




    //------------------------Billing---------------------------------
    public record BillResponse(Long id, Long patientId, Long appointmentId, BigDecimal amount,
                               BillStatus status, String description, LocalDateTime issuedAt,
                               LocalDateTime paidAt){
        public static BillResponse from(Bill bill){
            return new BillResponse(bill.getId(),
                    bill.getPatient().getId(),
                    bill.getAppointment() == null ? null: bill.getAppointment().getId(),
                    bill.getAmount(),
                    bill.getStatus(),
                    bill.getDescription(),
                    bill.getIssuedAt(),
                    bill.getPaidAt());

        }
    }




}
