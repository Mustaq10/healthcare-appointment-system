package com.healthcare.appointment_system.repository;

import com.healthcare.appointment_system.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, UUID> {

    // Get all medical records for a patient
    List<MedicalRecord> findByPatientIdOrderByCreatedAtDesc(UUID patientId);

    // Get all medical records created by a doctor
    List<MedicalRecord> findByDoctorIdOrderByCreatedAtDesc(UUID doctorId);

    // Get medical record for a specific appointment
    java.util.Optional<MedicalRecord> findByAppointmentId(UUID appointmentId);
}