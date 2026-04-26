package com.healthcare.appointment_system.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(precision = 10, scale = 2)
    private BigDecimal consultationFee;

    private Integer experienceYears;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "doctor_specializations",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "specialization_id")
    )
    @Builder.Default
    private Set<Specialization> specializations = new HashSet<>();

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<DoctorAvailability> availabilities;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<DoctorUnavailability> unavailabilities;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Appointment> appointments;
}