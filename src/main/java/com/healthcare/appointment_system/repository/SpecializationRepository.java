package com.healthcare.appointment_system.repository;

import com.healthcare.appointment_system.model.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
    Optional<Specialization> findByName(String name);
}