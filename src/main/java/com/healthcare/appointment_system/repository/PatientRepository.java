package com.healthcare.appointment_system.repository;

import com.healthcare.appointment_system.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    Optional<Patient> findByUserId(String userId);
    Boolean existsByUserId(String userId);
}