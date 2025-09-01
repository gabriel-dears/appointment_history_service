package com.hospital_app.appointment_history_service.infra.adapter.in.controller.graphql.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentHistoryResponse(
        UUID id,
        UUID appointmentId,
        UUID patientId,
        String patientEmail,
        String patientName,
        UUID doctorId,
        String doctorName,
        LocalDateTime dateTime,
        String status,
        String notes,
        Long version,
        LocalDateTime receivedAt
) {
}
