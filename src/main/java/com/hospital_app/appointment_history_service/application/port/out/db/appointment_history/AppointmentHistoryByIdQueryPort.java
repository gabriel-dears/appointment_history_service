package com.hospital_app.appointment_history_service.application.port.out.db.appointment_history;

import com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history.JpaAppointmentHistoryEntity;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface AppointmentHistoryByIdQueryPort {
    Page<JpaAppointmentHistoryEntity> findByAppointmentId(UUID appointmentId, int page, int size, UUID patientId, boolean lastVersionOnly);
}
