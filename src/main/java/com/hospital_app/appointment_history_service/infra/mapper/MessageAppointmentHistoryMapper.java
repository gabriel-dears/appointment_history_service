package com.hospital_app.appointment_history_service.infra.mapper;

import com.hospital_app.appointment_history_service.domain.model.AppointmentHistory;
import com.hospital_app.common.message.dto.AppointmentMessage;
import org.springframework.stereotype.Component;

@Component
public class MessageAppointmentHistoryMapper {

    public AppointmentHistory toHistory(AppointmentMessage appointmentMessage) {
        AppointmentHistory appointmentHistory = new AppointmentHistory();
        appointmentHistory.setNotes(appointmentMessage.getNotes());
        appointmentHistory.setDateTime(appointmentMessage.getDateTime());
        appointmentHistory.setAppointmentId(appointmentMessage.getId());
        appointmentHistory.setDoctorId(appointmentMessage.getDoctorId());
        appointmentHistory.setPatientId(appointmentMessage.getPatientId());
        appointmentHistory.setPatientName(appointmentMessage.getPatientName());
        appointmentHistory.setDoctorName(appointmentMessage.getDoctorName());
        appointmentHistory.setPatientEmail(appointmentMessage.getPatientEmail());
        appointmentHistory.setVersion(appointmentMessage.getVersion());
        appointmentHistory.setStatus(appointmentMessage.getStatus());
        return appointmentHistory;
    }

}
