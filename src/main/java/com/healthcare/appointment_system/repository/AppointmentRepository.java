package com.healthcare.appointment_system.repository;

import com.healthcare.appointment_system.model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    // Core conflict detection query
    // Checks if a doctor already has an appointment that overlaps with requested time
    @Query("""
        SELECT COUNT(a) > 0 FROM Appointment a
        WHERE a.doctor.id = :doctorId
        AND a.appointmentDate = :date
        AND a.status NOT IN ('CANCELLED', 'RESCHEDULED')
        AND (
            (a.startTime < :endTime AND
            FUNCTION('ADDTIME', a.startTime,
            FUNCTION('SEC_TO_TIME', a.durationMinutes * 60)) > :startTime)
        )
    """)
    Boolean existsConflict(
            @Param("doctorId") String doctorId,
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );

    // Get all appointments for a doctor on a specific date
    @Query("""
        SELECT a FROM Appointment a
        WHERE a.doctor.id = :doctorId
        AND a.appointmentDate = :date
        AND a.status NOT IN ('CANCELLED', 'RESCHEDULED')
        ORDER BY a.startTime ASC
    """)
    List<Appointment> findDoctorAppointmentsByDate(
            @Param("doctorId") String doctorId,
            @Param("date") LocalDate date
    );

    // Get paginated appointment history for a patient
    @Query("""
        SELECT a FROM Appointment a
        WHERE a.patient.id = :patientId
        ORDER BY a.appointmentDate DESC, a.startTime DESC
    """)
    Page<Appointment> findPatientHistory(
            @Param("patientId") String patientId,
            Pageable pageable
    );

    // Get paginated appointment history filtered by status
    @Query("""
        SELECT a FROM Appointment a
        WHERE a.patient.id = :patientId
        AND a.status = :status
        ORDER BY a.appointmentDate DESC
    """)
    Page<Appointment> findPatientHistoryByStatus(
            @Param("patientId") String patientId,
            @Param("status") Appointment.AppointmentStatus status,
            Pageable pageable
    );

    // Get all upcoming appointments for a doctor
    @Query("""
        SELECT a FROM Appointment a
        WHERE a.doctor.id = :doctorId
        AND a.appointmentDate >= :today
        AND a.status IN ('PENDING', 'CONFIRMED')
        ORDER BY a.appointmentDate ASC, a.startTime ASC
    """)
    List<Appointment> findUpcomingDoctorAppointments(
            @Param("doctorId") String doctorId,
            @Param("today") LocalDate today
    );
}