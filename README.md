# DATABASE_DESIGN.md

## Overview

This document defines a production-ready database schema for a healthcare appointment system built using Spring Boot. The design focuses on scalability, data integrity, and real-world healthcare requirements.

---

## Core Principles

* Scalable role-based access control (RBAC)
* Prevention of double booking
* Support for flexible scheduling
* Auditability and compliance
* Extensibility for future features

---

## 1. Users & Roles

### users

Stores authentication and basic profile details.

```
users
├── id (UUID, PK)
├── full_name (VARCHAR 100, NOT NULL)
├── email (VARCHAR 100, UNIQUE, NOT NULL)
├── password (VARCHAR 255, NOT NULL)
├── phone_number (VARCHAR 15, UNIQUE)
├── is_active (BOOLEAN, DEFAULT true)
├── is_deleted (BOOLEAN, DEFAULT false)
├── last_login (TIMESTAMP)
├── failed_attempts (INT, DEFAULT 0)
├── account_locked (BOOLEAN, DEFAULT false)
├── created_at (TIMESTAMP)
└── updated_at (TIMESTAMP)
```

### roles

```
roles
├── id (PK)
├── name (VARCHAR 50, UNIQUE)
```

### user_roles

```
user_roles
├── user_id (FK → users.id)
├── role_id (FK → roles.id)
```

---

## 2. Doctors & Patients

### doctors

```
doctors
├── id (UUID, PK)
├── user_id (FK → users.id, UNIQUE)
├── bio (TEXT)
├── consultation_fee (DECIMAL 10,2)
├── experience_years (INT)
├── created_at (TIMESTAMP)
```

### patients

```
patients
├── id (UUID, PK)
├── user_id (FK → users.id, UNIQUE)
├── date_of_birth (DATE)
├── gender (ENUM: MALE, FEMALE, OTHER)
├── blood_group (VARCHAR 5)
├── created_at (TIMESTAMP)
```

---

## 3. Specializations

### specializations

```
specializations
├── id (PK)
├── name (VARCHAR 100, UNIQUE)
```

### doctor_specializations

```
doctor_specializations
├── doctor_id (FK → doctors.id)
├── specialization_id (FK → specializations.id)
```

---

## 4. Scheduling System

### doctor_availability (weekly pattern)

```
doctor_availability
├── id (PK)
├── doctor_id (FK → doctors.id)
├── day_of_week (ENUM: MONDAY...SUNDAY)
├── start_time (TIME)
├── end_time (TIME)
├── slot_duration_minutes (INT)
```

### doctor_unavailability (exceptions)

```
doctor_unavailability
├── id (PK)
├── doctor_id (FK → doctors.id)
├── date (DATE)
├── start_time (TIME)
├── end_time (TIME)
├── reason (VARCHAR 255)
```

---

## 5. Appointments

### appointments

```
appointments
├── id (UUID, PK)
├── patient_id (FK → patients.id)
├── doctor_id (FK → doctors.id)
├── appointment_date (DATE)
├── start_time (TIME)
├── duration_minutes (INT)
├── status (ENUM: PENDING, CONFIRMED, CANCELLED, COMPLETED, RESCHEDULED)
├── reason (TEXT)
├── notes (TEXT)
├── created_at (TIMESTAMP)
├── updated_at (TIMESTAMP)

UNIQUE (doctor_id, appointment_date, start_time)
INDEX (doctor_id, appointment_date)
INDEX (patient_id)
```

### appointment_status_history

```
appointment_status_history
├── id (PK)
├── appointment_id (FK → appointments.id)
├── status (VARCHAR 50)
├── changed_at (TIMESTAMP)
```

---

## 6. Payments

### payments

```
payments
├── id (PK)
├── appointment_id (FK → appointments.id)
├── amount (DECIMAL 10,2)
├── status (ENUM: PAID, FAILED, REFUNDED)
├── payment_method (VARCHAR 50)
├── created_at (TIMESTAMP)
```

---

## 7. Medical Records

### medical_records

```
medical_records
├── id (PK)
├── patient_id (FK → patients.id)
├── doctor_id (FK → doctors.id)
├── diagnosis (TEXT)
├── prescription (TEXT)
├── attachments (TEXT)
├── created_at (TIMESTAMP)
```

---

## 8. Audit Logs

### audit_logs

```
audit_logs
├── id (PK)
├── user_id (FK → users.id)
├── action (VARCHAR 100)
├── entity_type (VARCHAR 50)
├── entity_id (UUID)
├── old_value (JSON)
├── new_value (JSON)
├── timestamp (TIMESTAMP)
```

---

## Relationships Summary

```
users ──────────── user_roles (many-to-many)
users ──────────── doctors (one-to-one)
users ──────────── patients (one-to-one)
doctors ─────────── doctor_specializations (many-to-many)
doctors ─────────── doctor_availability (one-to-many)
doctors ─────────── doctor_unavailability (one-to-many)
doctors ─────────── appointments (one-to-many)
patients ─────────── appointments (one-to-many)
appointments ─────── payments (one-to-one)
appointments ─────── appointment_status_history (one-to-many)
patients ─────────── medical_records (one-to-many)
users ──────────── audit_logs (one-to-many)
```

---

## Key Improvements Over Initial Design

* Replaced ENUM roles with RBAC tables
* Prevented double booking using UNIQUE constraint
* Introduced flexible scheduling (availability + exceptions)
* Added payments system
* Added medical records (core healthcare feature)
* Improved audit logging with JSON
* Added indexing for performance
* Added soft delete support
* Switched to UUID for scalability

---

## Notes for Implementation

* Use Hibernate UUID generator for IDs
* Use @ManyToMany for user_roles and doctor_specializations
* Add validation to prevent overlapping appointments
* Use transactional service layer for booking logic
* Ensure timezone handling for scheduling

---

## Next Steps

* Convert schema to JPA entities
* Implement booking service with conflict checks
* Add REST APIs
* Integrate authentication (Spring Security + JWT)

---

**This schema is production-ready and suitable for real-world healthcare applications.**
