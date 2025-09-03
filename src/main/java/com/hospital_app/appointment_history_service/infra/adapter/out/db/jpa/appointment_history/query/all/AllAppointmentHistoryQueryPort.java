package com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history.query.all;

import com.hospital_app.appointment_history_service.application.port.out.db.appointment_history.AppointmentHistoryQueryPort;
import com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history.JpaAppointmentHistoryEntity;
import com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history.JpaAppointmentHistoryRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
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
                PageRequest.of(page, size)
        );
    }

    public Specification<JpaAppointmentHistoryEntity> filter(
            UUID patientId,
            UUID doctorId,
            String patientName,
            String doctorName,
            String status,
            OffsetDateTime dateTime
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (patientId != null) predicates.add(cb.equal(root.get("patientId"), patientId));
            if (doctorId != null) predicates.add(cb.equal(root.get("doctorId"), doctorId));
            if (patientName != null) predicates.add(cb.like(cb.lower(root.get("patientName")), "%" + patientName.toLowerCase() + "%"));
            if (doctorName != null) predicates.add(cb.like(cb.lower(root.get("doctorName")), "%" + doctorName.toLowerCase() + "%"));
            if (status != null) predicates.add(cb.equal(root.get("status"), status));
            if (dateTime != null) predicates.add(cb.equal(root.get("dateTime"), dateTime));
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }


}
