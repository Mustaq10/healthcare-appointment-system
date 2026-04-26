package com.healthcare.appointment_system.repository;

import com.healthcare.appointment_system.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
    Optional<Doctor> findByUserId(String userId);
    Boolean existsByUserId(String userId);
}