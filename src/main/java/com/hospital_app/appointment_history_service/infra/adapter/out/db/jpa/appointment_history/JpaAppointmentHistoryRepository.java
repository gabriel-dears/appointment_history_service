package com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaAppointmentHistoryRepository extends JpaRepository<JpaAppointmentHistoryEntity, UUID> {
}
