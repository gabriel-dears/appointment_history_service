package com.hospital_app.appointment_history_service.application.exception;

public class AppointmentHistoryDbException extends RuntimeException {
    public AppointmentHistoryDbException(String message, Throwable cause) {
        super(message, cause);
    }
}

