package com.hospital_app.appointment_history_service.application.port.in.appointment_history;

import com.hospital_app.appointment_history_service.infra.adapter.in.controller.graphql.dto.AppointmentHistoryWithContext;

public interface FindAllAppointmentHistoryUseCase {
    AppointmentHistoryWithContext execute(boolean lastVersionOnly,
                                          int page,
                                          int size,
                                          AppointmentDateTimeScope appointmentDateTimeScope);
}
