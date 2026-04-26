package com.healthcare.appointment_system.audit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    // Get all actions performed by a specific user
    List<AuditLog> findByUserIdOrderByTimestampDesc(UUID userId);

    // Get full history of a specific record
    List<AuditLog> findByEntityTypeAndEntityIdOrderByTimestampDesc(
            String entityType,
            UUID entityId
    );

    // Get paginated audit logs for admin view
    Page<AuditLog> findAllByOrderByTimestampDesc(Pageable pageable);

    // Get audit logs within a time range
    List<AuditLog> findByTimestampBetweenOrderByTimestampDesc(
            LocalDateTime from,
            LocalDateTime to
    );

    // Get all logs for a specific action type
    List<AuditLog> findByActionOrderByTimestampDesc(String action);
}