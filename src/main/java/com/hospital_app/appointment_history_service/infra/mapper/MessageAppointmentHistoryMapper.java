package com.hospital_app.appointment_history_service.infra.mapper;

import com.hospital_app.appointment_history_service.domain.model.AppointmentHistory;
import com.hospital_app.common.message.dto.AppointmentMessage;
import org.springframework.stereotype.Component;

@Component
public class MessageAppointmentHistoryMapper {

    public AppointmentHistory toHistory(AppointmentMessage appointmentMessage) {
        AppointmentHistory appointmentHistory = new AppointmentHistory();
        appointmentHistory.setId(appointmentHistory.getId());
        appointmentHistory.setNotes(appointmentHistory.getNotes());
        appointmentHistory.setDateTime(appointmentHistory.getDateTime());
        appointmentHistory.setAppointmentId(appointmentHistory.getAppointmentId());
        appointmentHistory.setDoctorId(appointmentHistory.getDoctorId());
        appointmentHistory.setPatientId(appointmentHistory.getPatientId());
        appointmentHistory.setPatientName(appointmentHistory.getPatientName());
        appointmentHistory.setDoctorName(appointmentHistory.getDoctorName());
        appointmentHistory.setPatientEmail(appointmentHistory.getPatientEmail());
        appointmentHistory.setVersion(appointmentHistory.getVersion());
        appointmentHistory.setReceivedAt(appointmentHistory.getReceivedAt());
        return appointmentHistory;
    }

}
