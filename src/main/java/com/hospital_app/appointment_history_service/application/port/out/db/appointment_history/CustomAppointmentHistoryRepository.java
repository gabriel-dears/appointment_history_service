package com.hospital_app.appointment_history_service.application.port.out.db.appointment_history;

import com.hospital_app.appointment_history_service.domain.model.AppointmentHistory;

public interface CustomAppointmentHistoryRepository {

    AppointmentHistory create(AppointmentHistory appointmentHistory);

}
