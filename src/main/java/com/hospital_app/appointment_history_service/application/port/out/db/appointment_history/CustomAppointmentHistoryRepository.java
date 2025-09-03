package com.hospital_app.appointment_history_service.application.port.out.db.appointment_history;

import com.hospital_app.appointment_history_service.application.port.in.appointment_history.AppointmentDateTimeScope;
import com.hospital_app.appointment_history_service.domain.model.AppointmentHistory;
import com.hospital_app.common.db.pagination.ApplicationPage;

import java.util.UUID;

public interface CustomAppointmentHistoryRepository {

    AppointmentHistory create(AppointmentHistory appointmentHistory);

    ApplicationPage<AppointmentHistory> findByAppointmentId(UUID appointmentId, boolean lastVersionOnly, int page, int size, UUID patientId, AppointmentDateTimeScope appointmentDateTimeScope);

    ApplicationPage<AppointmentHistory> findAll(
            boolean lastVersionOnly,
            int page,
            int size,
            UUID patientId, String patientName,
            String doctorName,
            String status,
            AppointmentDateTimeScope appointmentDateTimeScope
    );

}
