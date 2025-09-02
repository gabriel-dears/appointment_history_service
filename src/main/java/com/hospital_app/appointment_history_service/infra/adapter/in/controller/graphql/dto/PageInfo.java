package com.hospital_app.appointment_history_service.infra.adapter.in.controller.graphql.dto;

import com.hospital_app.common.db.pagination.ApplicationPage;

public record PageInfo(
        int pageNumber,
        int pageSize,
        int totalPages,
        long totalElements,
        boolean isFirst,
        boolean isLast
) {

    public static PageInfo from(ApplicationPage<?> applicationPage) {
        return new PageInfo(
                applicationPage.getPageNumber(),
                applicationPage.getPageSize(),
                applicationPage.getTotalPages(),
                applicationPage.getTotalElements(),
                applicationPage.isFirst(),
                applicationPage.isLast()
        );
    }

}
