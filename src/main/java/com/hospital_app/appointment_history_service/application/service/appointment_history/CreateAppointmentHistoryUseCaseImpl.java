package com.hospital_app.appointment_history_service.application.service.appointment_history;

import com.hospital_app.appointment_history_service.application.port.in.appointment_history.CreateAppointmentHistoryUseCase;
import com.hospital_app.appointment_history_service.application.port.out.db.appointment_history.CustomAppointmentHistoryRepository;
import com.hospital_app.appointment_history_service.domain.model.AppointmentHistory;

public class CreateAppointmentHistoryUseCaseImpl implements CreateAppointmentHistoryUseCase {

    private final CustomAppointmentHistoryRepository customAppointmentHistoryRepository;

    public CreateAppointmentHistoryUseCaseImpl(CustomAppointmentHistoryRepository customAppointmentHistoryRepository) {
        this.customAppointmentHistoryRepository = customAppointmentHistoryRepository;
    }

    @Override
    public AppointmentHistory createAppointmentHistory(AppointmentHistory appointmentHistory) {
        return customAppointmentHistoryRepository.create(appointmentHistory);
    }

}
