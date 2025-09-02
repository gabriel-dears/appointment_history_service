package com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history.query.all;

import com.hospital_app.appointment_history_service.application.port.out.db.appointment_history.AppointmentHistoryQueryPort;
import com.hospital_app.appointment_history_service.application.port.out.db.appointment_history.CustomAppointmentHistoryRepository;
import com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history.JpaAppointmentHistoryEntity;
import com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history.JpaAppointmentHistoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class AllAppointmentHistoryQueryPort implements AppointmentHistoryQueryPort {

    // TODO: create functions using new Specifications... dateTimeAfter and dateTimeBefore

    private final JpaAppointmentHistoryRepository repository;
    private final CustomAppointmentHistoryRepository customAppointmentHistoryRepository;

    public AllAppointmentHistoryQueryPort(JpaAppointmentHistoryRepository repository, CustomAppointmentHistoryRepository customAppointmentHistoryRepository) {
        this.repository = repository;
        this.customAppointmentHistoryRepository = customAppointmentHistoryRepository;
    }

    @Override
    public Page<JpaAppointmentHistoryEntity> findAll(Pageable pageable, boolean lastVersionOnly) {
        if (lastVersionOnly) {
            return repository.findAllByLastVersion(pageable);
        }

        return repository.findAll(pageable);
    }
}
