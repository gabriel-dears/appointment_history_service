package com.hospital_app.appointment_history_service.application.service.appointment_history;

import com.hospital_app.appointment_history_service.application.port.in.appointment_history.AppointmentDateTimeScope;
import com.hospital_app.appointment_history_service.application.port.in.appointment_history.FindAllAppointmentHistoryUseCase;
import com.hospital_app.appointment_history_service.application.port.out.db.appointment_history.CustomAppointmentHistoryRepository;
import com.hospital_app.appointment_history_service.domain.model.AppointmentHistory;
import com.hospital_app.appointment_history_service.infra.adapter.in.controller.graphql.dto.AppointmentHistoryConnection;
import com.hospital_app.appointment_history_service.infra.adapter.in.controller.graphql.dto.AppointmentHistoryResponse;
import com.hospital_app.appointment_history_service.infra.adapter.in.controller.graphql.dto.PageInfo;
import com.hospital_app.common.db.pagination.ApplicationPage;

import java.time.OffsetDateTime;
import java.util.UUID;

public class FindAllAppointmentHistoryUseCaseImpl implements FindAllAppointmentHistoryUseCase {

    private final CustomAppointmentHistoryRepository customAppointmentHistoryRepository;

    public FindAllAppointmentHistoryUseCaseImpl(CustomAppointmentHistoryRepository customAppointmentHistoryRepository) {
        this.customAppointmentHistoryRepository = customAppointmentHistoryRepository;
    }


    @Override
    public AppointmentHistoryConnection execute(boolean lastVersionOnly, int page, int size, UUID patientId, UUID doctorId, String patientName, String doctorName, String status, OffsetDateTime dateTime, AppointmentDateTimeScope appointmentDateTimeScope) {
        ApplicationPage<AppointmentHistory> historyApplicationPage = customAppointmentHistoryRepository.findAll(
                lastVersionOnly,
                page,
                size,
                patientId,
                doctorId,
                patientName,
                doctorName,
                status,
                dateTime,
                appointmentDateTimeScope
        );
        return new AppointmentHistoryConnection(
                PageInfo.from(historyApplicationPage),
                historyApplicationPage.getContent().stream().map(AppointmentHistoryResponse::from).toList()
        );
    }
}
