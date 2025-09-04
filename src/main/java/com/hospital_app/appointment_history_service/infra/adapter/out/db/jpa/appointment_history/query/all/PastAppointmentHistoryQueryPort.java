package com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history.query.all;

import com.hospital_app.appointment_history_service.application.port.out.db.appointment_history.AppointmentHistoryQueryPort;
import com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history.JpaAppointmentHistoryEntity;
import com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history.JpaAppointmentHistoryRepository;
import com.hospital_app.appointment_history_service.infra.db.AppointmentHistoryDbOperationWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Component
public class PastAppointmentHistoryQueryPort implements AppointmentHistoryQueryPort {

    private final JpaAppointmentHistoryRepository repository;

    public PastAppointmentHistoryQueryPort(JpaAppointmentHistoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<JpaAppointmentHistoryEntity> findAll(boolean lastVersionOnly, int page, int size, UUID patientId, String patientName, String doctorName, String status, LocalDate startDate, LocalDate endDate, String patientEmail) {

        if (lastVersionOnly) {
            return AppointmentHistoryDbOperationWrapper.execute(() -> repository.searchAppointmentHistoriesLastVersionPast(
                    patientId,
                    patientName,
                    doctorName,
                    status,
                    OffsetDateTime.now(),
                    startDate,
                    endDate,
                    patientEmail,
                    PageRequest.of(page, size)
            ));
        }

        return AppointmentHistoryDbOperationWrapper.execute(() -> repository.searchAppointmentHistoriesPast(
                patientId,
                patientName,
                doctorName,
                status,
                OffsetDateTime.now(),
                startDate,
                endDate,
                patientEmail,
                PageRequest.of(page, size)
        ));
    }
}
