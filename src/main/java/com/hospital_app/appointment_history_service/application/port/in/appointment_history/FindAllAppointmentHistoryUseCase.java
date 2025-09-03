package com.hospital_app.appointment_history_service.application.port.in.appointment_history;

import com.hospital_app.appointment_history_service.infra.adapter.in.controller.graphql.dto.AppointmentHistoryConnection;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface FindAllAppointmentHistoryUseCase {
    AppointmentHistoryConnection execute(boolean lastVersionOnly,
                                         int page,
                                         int size,
                                         UUID patientId,
                                         String patientName,
                                         String doctorName,
                                         String status,
                                         AppointmentDateTimeScope appointmentDateTimeScope);
}
