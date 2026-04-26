package com.healthcare.appointment_system.audit;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "audit_logs",
        indexes = {
                @Index(name = "idx_audit_user", columnList = "user_id"),
                @Index(name = "idx_audit_entity", columnList = "entity_type, entity_id"),
                @Index(name = "idx_audit_timestamp", columnList = "timestamp")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Who performed the action
    @Column(name = "user_id")
    private UUID userId;

    // What action was performed e.g. BOOK_APPOINTMENT, CANCEL_APPOINTMENT
    @Column(nullable = false, length = 100)
    private String action;

    // Which type of record was affected e.g. APPOINTMENT, USER
    @Column(nullable = false, length = 50)
    private String entityType;

    // Which specific record was affected
    @Column(name = "entity_id")
    private UUID entityId;

    // What the record looked like before the change
    @Column(columnDefinition = "TEXT")
    private String oldValue;

    // What the record looks like after the change
    @Column(columnDefinition = "TEXT")
    private String newValue;

    // IP address of the request for security tracking
    @Column(length = 50)
    private String ipAddress;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @PrePersist
    public void prePersist() {
        this.timestamp = LocalDateTime.now();
    }
}