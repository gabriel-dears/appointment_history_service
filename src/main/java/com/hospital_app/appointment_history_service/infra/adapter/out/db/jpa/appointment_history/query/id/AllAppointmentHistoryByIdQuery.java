package com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history.query.id;

import com.hospital_app.appointment_history_service.application.port.out.db.appointment_history.AppointmentHistoryByIdQueryPort;
import com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history.JpaAppointmentHistoryEntity;
import com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history.JpaAppointmentHistoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AllAppointmentHistoryByIdQuery implements AppointmentHistoryByIdQueryPort {

    private final JpaAppointmentHistoryRepository repository;

    public AllAppointmentHistoryByIdQuery(JpaAppointmentHistoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<JpaAppointmentHistoryEntity> findByAppointmentId(UUID appointmentId, Pageable pageable, boolean lastVersionOnly) {
        if (lastVersionOnly) {
            return repository.findByLastVersionAppointmentId(appointmentId, pageable);
        } else {
            return repository.findByAppointmentIdOrderByVersionAsc(appointmentId, pageable);
        }
    }
}
