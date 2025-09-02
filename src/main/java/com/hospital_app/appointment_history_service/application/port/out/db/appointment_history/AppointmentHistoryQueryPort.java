package com.hospital_app.appointment_history_service.application.port.out.db.appointment_history;

import com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history.JpaAppointmentHistoryEntity;
import org.springframework.data.domain.Page;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface AppointmentHistoryQueryPort {

    // TODO: by Id, remove pageable -> page, size

    Page<JpaAppointmentHistoryEntity> findAll(
            boolean lastVersionOnly,
            int page,
            int size,
            UUID patientId,
            UUID doctorId,
            String patientName,
            String doctorName,
            String status,
            OffsetDateTime dateTime);

}
