package com.healthcare.appointment_system.repository;

import com.healthcare.appointment_system.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    Optional<Payment> findByAppointmentId(String appointmentId);
}