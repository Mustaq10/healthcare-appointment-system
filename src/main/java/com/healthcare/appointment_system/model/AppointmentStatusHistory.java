package com.healthcare.appointment_system.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointment_status_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Appointment.AppointmentStatus status;

    @Column(nullable = false)
    private LocalDateTime changedAt;

    @Column(length = 255)
    private String changedBy;

    @Column(length = 255)
    private String reason;

    // Auto set timestamp on creation
    @PrePersist
    public void prePersist() {
        this.changedAt = LocalDateTime.now();
    }
}