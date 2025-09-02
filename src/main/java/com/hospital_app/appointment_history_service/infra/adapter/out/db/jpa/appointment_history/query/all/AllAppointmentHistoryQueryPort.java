package com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history.query.all;

import com.hospital_app.appointment_history_service.application.port.out.db.appointment_history.AppointmentHistoryQueryPort;
import com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history.JpaAppointmentHistoryEntity;
import com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history.JpaAppointmentHistoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.UUID;

@Component
public class AllAppointmentHistoryQueryPort implements AppointmentHistoryQueryPort {

    private final JpaAppointmentHistoryRepository jpaAppointmentHistoryRepository;

    public AllAppointmentHistoryQueryPort(JpaAppointmentHistoryRepository jpaAppointmentHistoryRepository) {
        this.jpaAppointmentHistoryRepository = jpaAppointmentHistoryRepository;
    }


    @Override
    public Page<JpaAppointmentHistoryEntity> findAll(boolean lastVersionOnly, int page, int size, UUID patientId, UUID doctorId, String patientName, String doctorName, String status, OffsetDateTime dateTime) {

        if (lastVersionOnly) {
            return jpaAppointmentHistoryRepository.searchAppointmentHistoriesLastVersionAll(
                    patientId,
                    doctorId,
                    patientName,
                    doctorName,
                    status,
                    dateTime,
                    PageRequest.of(page, size)
            );
        }

        return jpaAppointmentHistoryRepository.searchAppointmentHistoriesAll(
                patientId,
                doctorId,
                patientName,
                doctorName,
                status,
                dateTime,
                PageRequest.of(page, size)
        );
    }
}
