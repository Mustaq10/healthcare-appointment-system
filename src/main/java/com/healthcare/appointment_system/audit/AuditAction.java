package com.healthcare.appointment_system.audit;

public final class AuditAction {

    private AuditAction() {}

    // User actions
    public static final String USER_REGISTERED = "USER_REGISTERED";
    public static final String USER_LOGIN = "USER_LOGIN";
    public static final String USER_LOGIN_FAILED = "USER_LOGIN_FAILED";
    public static final String USER_LOCKED = "USER_LOCKED";
    public static final String USER_UPDATED = "USER_UPDATED";
    public static final String USER_DEACTIVATED = "USER_DEACTIVATED";

    // Appointment actions
    public static final String APPOINTMENT_BOOKED = "APPOINTMENT_BOOKED";
    public static final String APPOINTMENT_CONFIRMED = "APPOINTMENT_CONFIRMED";
    public static final String APPOINTMENT_CANCELLED = "APPOINTMENT_CANCELLED";
    public static final String APPOINTMENT_RESCHEDULED = "APPOINTMENT_RESCHEDULED";
    public static final String APPOINTMENT_COMPLETED = "APPOINTMENT_COMPLETED";

    // Doctor actions
    public static final String DOCTOR_SCHEDULE_UPDATED = "DOCTOR_SCHEDULE_UPDATED";
    public static final String DOCTOR_UNAVAILABILITY_ADDED = "DOCTOR_UNAVAILABILITY_ADDED";

    // Medical record actions
    public static final String MEDICAL_RECORD_CREATED = "MEDICAL_RECORD_CREATED";
    public static final String MEDICAL_RECORD_UPDATED = "MEDICAL_RECORD_UPDATED";

    // Payment actions
    public static final String PAYMENT_COMPLETED = "PAYMENT_COMPLETED";
    public static final String PAYMENT_FAILED = "PAYMENT_FAILED";
    public static final String PAYMENT_REFUNDED = "PAYMENT_REFUNDED";
}