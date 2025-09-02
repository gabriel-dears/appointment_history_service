package com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history.query.all;

import com.hospital_app.appointment_history_service.application.port.out.db.appointment_history.AppointmentHistoryQueryPort;
import com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history.JpaAppointmentHistoryEntity;
import com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history.JpaAppointmentHistoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class AllAppointmentHistoryQueryPort implements AppointmentHistoryQueryPort {

    private final JpaAppointmentHistoryRepository repository;

    public AllAppointmentHistoryQueryPort(JpaAppointmentHistoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<JpaAppointmentHistoryEntity> findAll(Pageable pageable, boolean lastVersionOnly) {
        if(lastVersionOnly) {
            return repository.findAllByLastVersion(pageable);
        }

        return repository.findAll(pageable);
    }
}
