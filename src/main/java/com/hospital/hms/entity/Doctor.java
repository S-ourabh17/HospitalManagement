package com.hospital.hms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Table(name ="doctors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, length = 60)
    private String firstName;

    @Column(nullable = false, length = 60)
    private String lastName;

    @Column(nullable = false, length = 100)
    private String specialization;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, precision = 10, scale = 2)                                 // Used for Number with decimal
    private BigDecimal consultationFee;


    @Column(nullable = false)
    @Builder.Default
    private boolean active = true;




}
