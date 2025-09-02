package com.hospital_app.appointment_history_service.infra.adapter.in.controller.graphql.dto;

import java.util.List;

public record AppointmentHistoryConnection(
        PageInfo pageInfo,
        List<AppointmentHistoryResponse> content) {
}
