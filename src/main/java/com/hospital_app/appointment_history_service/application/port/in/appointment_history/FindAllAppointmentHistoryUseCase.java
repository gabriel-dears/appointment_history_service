package com.hospital_app.appointment_history_service.application.port.in.appointment_history;

import com.hospital_app.appointment_history_service.infra.adapter.in.controller.graphql.dto.AppointmentHistoryConnection;

import java.time.OffsetDateTime;

public interface FindAllAppointmentHistoryUseCase {
    AppointmentHistoryConnection execute(boolean lastVersionOnly,
                                         int page,
                                         int size,
                                         String patientName,
                                         String doctorName,
                                         String status,
                                         OffsetDateTime dateTime,
                                         AppointmentDateTimeScope appointmentDateTimeScope);
}
