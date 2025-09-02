package com.hospital_app.appointment_history_service.infra.mapper;

import com.hospital_app.appointment_history_service.domain.model.AppointmentHistory;
import com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history.JpaAppointmentHistoryEntity;
import org.springframework.stereotype.Component;

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
        jpaAppointmentHistoryEntity.setReceivedAt(appointmentHistory.getReceivedAt().atOffset(ZoneOffset.UTC));
        jpaAppointmentHistoryEntity.setStatus(appointmentHistory.getStatus());
        return jpaAppointmentHistoryEntity;
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
        appointmentHistory.setReceivedAt(jpaAppointmentHistoryEntity.getReceivedAt().toLocalDateTime());
        appointmentHistory.setStatus(jpaAppointmentHistoryEntity.getStatus());
        appointmentHistory.setAppointmentId(jpaAppointmentHistoryEntity.getAppointmentId());
        return appointmentHistory;
    }
}
