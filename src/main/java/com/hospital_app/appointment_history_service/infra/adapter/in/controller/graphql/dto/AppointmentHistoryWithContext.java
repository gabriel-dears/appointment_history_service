package com.hospital_app.appointment_history_service.infra.adapter.in.controller.graphql.dto;

import java.util.UUID;

public record AppointmentHistoryWithContext(
        UUID appointmentId,
        AppointmentHistoryConnection history
) {
}
