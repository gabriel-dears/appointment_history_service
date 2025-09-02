package com.hospital_app.appointment_history_service.application.service.appointment_history;

import com.hospital_app.appointment_history_service.application.port.in.appointment_history.AppointmentDateTimeScope;
import com.hospital_app.appointment_history_service.application.port.in.appointment_history.FindAllAppointmentHistoryUseCase;
import com.hospital_app.appointment_history_service.application.port.out.db.appointment_history.CustomAppointmentHistoryRepository;
import com.hospital_app.appointment_history_service.domain.model.AppointmentHistory;
import com.hospital_app.appointment_history_service.infra.adapter.in.controller.graphql.dto.AppointmentHistoryConnection;
import com.hospital_app.appointment_history_service.infra.adapter.in.controller.graphql.dto.AppointmentHistoryResponse;
import com.hospital_app.appointment_history_service.infra.adapter.in.controller.graphql.dto.AppointmentHistoryWithContext;
import com.hospital_app.appointment_history_service.infra.adapter.in.controller.graphql.dto.PageInfo;
import com.hospital_app.common.db.pagination.ApplicationPage;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class FindAllAppointmentHistoryUseCaseImpl implements FindAllAppointmentHistoryUseCase {

    private final CustomAppointmentHistoryRepository customAppointmentHistoryRepository;

    public FindAllAppointmentHistoryUseCaseImpl(CustomAppointmentHistoryRepository customAppointmentHistoryRepository) {
        this.customAppointmentHistoryRepository = customAppointmentHistoryRepository;
    }

    @Override
    public AppointmentHistoryWithContext execute(boolean lastVersionOnly, int page, int size, AppointmentDateTimeScope appointmentDateTimeScope) {

        ApplicationPage<AppointmentHistory> historyApplicationPage = customAppointmentHistoryRepository.findAll(lastVersionOnly, page, size, appointmentDateTimeScope);

        Map<UUID, List<AppointmentHistory>> historyMap = historyApplicationPage.getContent().stream().collect(Collectors.groupingBy(AppointmentHistory::getAppointmentId));

        // TODO: create logic to create a AppointmentHistoryConnection for each appointmentId
        // TODO: IMPOSSIBLE TO CONTROL PAGINATION for each appointment

        new AppointmentHistoryConnection(
                PageInfo.from(historyApplicationPage),
                historyApplicationPage.getContent().stream().map(AppointmentHistoryResponse::from).toList()
        );

        return null;
    }
}
