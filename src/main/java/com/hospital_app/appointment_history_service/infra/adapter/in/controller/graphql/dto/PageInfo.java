package com.hospital_app.appointment_history_service.infra.adapter.in.controller.graphql.dto;

public record PageInfo(
        int pageNumber,
        int pageSize,
        int totalPages,
        int totalElements,
        boolean isFirst,
        boolean isLast
) {
}
