package com.hospital.hms.entity;

import com.hospital.hms.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String firstName;

    @Column(nullable = false, length = 60)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(unique = true)
    private String email;

    private String address;

    @Column(length = 5)
    private String bloodGroup;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;


    @PrePersist
    void onCreate(){
        this.createdAt = LocalDateTime.now();
    }



}
