package com.hospital_app.appointment_history_service.infra.adapter.in.controller.graphql.dto;

import com.hospital_app.appointment_history_service.domain.model.AppointmentHistory;

import java.util.List;

public record AppointmentHistoryConnection(
        PageInfo pageInfo,
        List<AppointmentHistory> content) {
}
