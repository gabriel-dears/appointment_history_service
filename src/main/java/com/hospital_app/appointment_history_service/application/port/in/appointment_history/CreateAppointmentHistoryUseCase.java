package com.hospital_app.appointment_history_service.application.port.in.appointment_history;

import com.hospital_app.appointment_history_service.domain.model.AppointmentHistory;

public interface CreateAppointmentHistoryUseCase {
    AppointmentHistory createAppointmentHistory(AppointmentHistory appointmentHistory);
}
