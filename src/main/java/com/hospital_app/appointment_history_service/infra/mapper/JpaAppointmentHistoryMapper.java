package com.hospital_app.appointment_history_service.infra.mapper;

import com.hospital_app.appointment_history_service.domain.model.AppointmentHistory;
import com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history.JpaAppointmentHistoryEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
public class JpaAppointmentHistoryMapper {

    public JpaAppointmentHistoryEntity toEntity(AppointmentHistory appointmentHistory) {
        JpaAppointmentHistoryEntity jpaAppointmentHistoryEntity = new JpaAppointmentHistoryEntity();
        jpaAppointmentHistoryEntity.setId(appointmentHistory.getId());
        jpaAppointmentHistoryEntity.setAppointmentId(appointmentHistory.getAppointmentId());
        jpaAppointmentHistoryEntity.setNotes(appointmentHistory.getNotes());
        jpaAppointmentHistoryEntity.setDateTime(appointmentHistory.getDateTime().atOffset(ZoneOffset.UTC));
        jpaAppointmentHistoryEntity.setDoctorId(appointmentHistory.getDoctorId());
        jpaAppointmentHistoryEntity.setDoctorName(appointmentHistory.getDoctorName());
        jpaAppointmentHistoryEntity.setAppointmentId(appointmentHistory.getAppointmentId());
        jpaAppointmentHistoryEntity.setPatientId(appointmentHistory.getPatientId());
        jpaAppointmentHistoryEntity.setPatientName(appointmentHistory.getPatientName());
        jpaAppointmentHistoryEntity.setPatientEmail(appointmentHistory.getPatientEmail());
        jpaAppointmentHistoryEntity.setVersion(appointmentHistory.getVersion());
        handleReceivedAt(appointmentHistory.getReceivedAt(), jpaAppointmentHistoryEntity);
        jpaAppointmentHistoryEntity.setStatus(appointmentHistory.getStatus());
        return jpaAppointmentHistoryEntity;
    }

    private static void handleReceivedAt(LocalDateTime receivedAt, JpaAppointmentHistoryEntity jpaAppointmentHistoryEntity) {
        if(receivedAt != null) {
            jpaAppointmentHistoryEntity.setReceivedAt(receivedAt.atOffset(ZoneOffset.UTC));
        }
    }

    public AppointmentHistory toDomain(JpaAppointmentHistoryEntity jpaAppointmentHistoryEntity) {
        AppointmentHistory appointmentHistory = new AppointmentHistory();
        appointmentHistory.setId(jpaAppointmentHistoryEntity.getId());
        appointmentHistory.setNotes(jpaAppointmentHistoryEntity.getNotes());
        appointmentHistory.setDateTime(jpaAppointmentHistoryEntity.getDateTime().toLocalDateTime());
        appointmentHistory.setPatientId(jpaAppointmentHistoryEntity.getPatientId());
        appointmentHistory.setDoctorId(jpaAppointmentHistoryEntity.getDoctorId());
        appointmentHistory.setDoctorName(jpaAppointmentHistoryEntity.getDoctorName());
        appointmentHistory.setPatientName(jpaAppointmentHistoryEntity.getPatientName());
        appointmentHistory.setPatientEmail(jpaAppointmentHistoryEntity.getPatientEmail());
        appointmentHistory.setVersion(jpaAppointmentHistoryEntity.getVersion());
        handleReceivedAt(jpaAppointmentHistoryEntity.getReceivedAt(), appointmentHistory);
        appointmentHistory.setStatus(jpaAppointmentHistoryEntity.getStatus());
        appointmentHistory.setAppointmentId(jpaAppointmentHistoryEntity.getAppointmentId());
        return appointmentHistory;
    }

    private static void handleReceivedAt(OffsetDateTime receivedAt, AppointmentHistory appointmentHistory) {
        if(receivedAt != null) {
            appointmentHistory.setReceivedAt(receivedAt.toLocalDateTime());
        }
    }

}
