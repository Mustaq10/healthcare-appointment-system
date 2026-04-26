package com.healthcare.appointment_system.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "medical_records",
        indexes = {
                @Index(name = "idx_medical_patient", columnList = "patient_id"),
                @Index(name = "idx_medical_doctor", columnList = "doctor_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalRecord extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @Column(columnDefinition = "TEXT")
    private String diagnosis;

    @Column(columnDefinition = "TEXT")
    private String prescription;

    // Stores file paths or URLs of attachments
    @Column(columnDefinition = "TEXT")
    private String attachments;

    @Column(columnDefinition = "TEXT")
    private String notes;
}