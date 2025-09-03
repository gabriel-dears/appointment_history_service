package com.hospital_app.appointment_history_service.application.port.in.appointment_history;

import com.hospital_app.appointment_history_service.infra.adapter.in.controller.graphql.dto.AppointmentHistoryConnection;

import java.util.UUID;

public interface FindByIdAppointmentHistoryUseCase {
    AppointmentHistoryConnection execute(UUID id,
                                         boolean lastVersionOnly,
                                         int page,
                                         int size,
                                         UUID patientId,
                                         AppointmentDateTimeScope appointmentDateTimeScope);
}
