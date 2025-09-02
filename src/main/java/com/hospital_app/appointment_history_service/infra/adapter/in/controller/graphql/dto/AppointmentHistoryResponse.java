package com.hospital_app.appointment_history_service.infra.adapter.in.controller.graphql.dto;

import com.hospital_app.appointment_history_service.domain.model.AppointmentHistory;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

public record AppointmentHistoryResponse(
        UUID id,
        UUID appointmentId,
        UUID patientId,
        String patientEmail,
        String patientName,
        UUID doctorId,
        String doctorName,
        OffsetDateTime dateTime,
        String status,
        String notes,
        Long version,
        OffsetDateTime receivedAt
) {

    public static AppointmentHistoryResponse from(AppointmentHistory appointmentHistory) {
        return new AppointmentHistoryResponse(
                appointmentHistory.getId(),
                appointmentHistory.getAppointmentId(),
                appointmentHistory.getPatientId(),
                appointmentHistory.getPatientEmail(),
                appointmentHistory.getPatientName(),
                appointmentHistory.getDoctorId(),
                appointmentHistory.getDoctorName(),
                appointmentHistory.getDateTime().atOffset(ZoneOffset.UTC),
                appointmentHistory.getStatus(),
                appointmentHistory.getNotes(),
                appointmentHistory.getVersion(),
                appointmentHistory.getReceivedAt().atOffset(ZoneOffset.UTC)
        );
    }
}


