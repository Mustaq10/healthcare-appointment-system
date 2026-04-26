package com.healthcare.appointment_system.audit;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    // Called from any service to log an action
    @Async
    public void log(
            UUID userId,
            String action,
            String entityType,
            UUID entityId,
            String oldValue,
            String newValue
    ) {
        AuditLog log = AuditLog.builder()
                .userId(userId)
                .action(action)
                .entityType(entityType)
                .entityId(entityId)
                .oldValue(oldValue)
                .newValue(newValue)
                .build();

        auditLogRepository.save(log);
    }

    // Overloaded version with IP address for auth events
    @Async
    public void log(
            UUID userId,
            String action,
            String entityType,
            UUID entityId,
            String oldValue,
            String newValue,
            String ipAddress
    ) {
        AuditLog log = AuditLog.builder()
                .userId(userId)
                .action(action)
                .entityType(entityType)
                .entityId(entityId)
                .oldValue(oldValue)
                .newValue(newValue)
                .ipAddress(ipAddress)
                .build();

        auditLogRepository.save(log);
    }
}