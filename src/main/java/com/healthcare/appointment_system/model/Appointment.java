package com.healthcare.appointment_system.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(
        name = "appointments",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_doctor_date_time",
                        columnNames = {"doctor_id", "appointment_date", "start_time"}
                )
        },
        indexes = {
                @Index(name = "idx_doctor_date", columnList = "doctor_id, appointment_date"),
                @Index(name = "idx_patient_id", columnList = "patient_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(nullable = false)
    private LocalDate appointmentDate;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private Integer durationMinutes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status;

    @Column(columnDefinition = "TEXT")
    private String reason;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL)
    private List<AppointmentStatusHistory> statusHistory;

    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL)
    private Payment payment;

    public enum AppointmentStatus {
        PENDING,
        CONFIRMED,
        CANCELLED,
        COMPLETED,
        RESCHEDULED
    }

    // Helper method — calculates end time from start + duration
    public LocalTime getEndTime() {
        return startTime.plusMinutes(durationMinutes);
    }
}