package com.hospital_app.appointment_history_service.application.service.appointment_history;

import com.hospital_app.appointment_history_service.application.port.in.appointment_history.AppointmentDateTimeScope;
import com.hospital_app.appointment_history_service.application.port.in.appointment_history.FindByIdAppointmentHistoryUseCase;
import com.hospital_app.appointment_history_service.application.port.out.db.appointment_history.CustomAppointmentHistoryRepository;
import com.hospital_app.appointment_history_service.domain.model.AppointmentHistory;
import com.hospital_app.appointment_history_service.infra.adapter.in.controller.graphql.dto.AppointmentHistoryConnection;
import com.hospital_app.appointment_history_service.infra.adapter.in.controller.graphql.dto.AppointmentHistoryResponse;
import com.hospital_app.appointment_history_service.infra.adapter.in.controller.graphql.dto.PageInfo;
import com.hospital_app.common.db.pagination.ApplicationPage;

import java.util.UUID;

public class FindByIdAppointmentHistoryUseCaseImpl implements FindByIdAppointmentHistoryUseCase {

    private final CustomAppointmentHistoryRepository customAppointmentHistoryRepository;

    public FindByIdAppointmentHistoryUseCaseImpl(CustomAppointmentHistoryRepository customAppointmentHistoryRepository) {
        this.customAppointmentHistoryRepository = customAppointmentHistoryRepository;
    }

    @Override
    public AppointmentHistoryConnection execute(UUID id, boolean lastVersionOnly, int page, int size, UUID patientId, AppointmentDateTimeScope appointmentDateTimeScope) {
        ApplicationPage<AppointmentHistory> historyApplicationPage = customAppointmentHistoryRepository.findByAppointmentId(id, lastVersionOnly, page, size, patientId, appointmentDateTimeScope);
        return new AppointmentHistoryConnection(
                PageInfo.from(historyApplicationPage),
                historyApplicationPage.getContent().stream().map(AppointmentHistoryResponse::from).toList()
        );
    }
}
