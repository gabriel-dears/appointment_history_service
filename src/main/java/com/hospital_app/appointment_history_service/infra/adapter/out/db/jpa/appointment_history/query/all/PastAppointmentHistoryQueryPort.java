package com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history.query.all;

import com.hospital_app.appointment_history_service.application.port.out.db.appointment_history.AppointmentHistoryQueryPort;
import com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history.JpaAppointmentHistoryEntity;
import com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history.JpaAppointmentHistoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class PastAppointmentHistoryQueryPort implements AppointmentHistoryQueryPort {

    private final JpaAppointmentHistoryRepository repository;

    public PastAppointmentHistoryQueryPort(JpaAppointmentHistoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<JpaAppointmentHistoryEntity> findAll(boolean lastVersionOnly, int page, int size, String patientName, String doctorName, String status, OffsetDateTime dateTime) {

        if (lastVersionOnly) {
            return repository.searchAppointmentHistoriesLastVersionPast(
                    patientName,
                    doctorName,
                    status,
                    OffsetDateTime.now(),
                    PageRequest.of(page, size)
            );
        }

        return repository.searchAppointmentHistoriesPast(
                patientName,
                doctorName,
                status,
                OffsetDateTime.now(),
                PageRequest.of(page, size)
        );
    }
}
