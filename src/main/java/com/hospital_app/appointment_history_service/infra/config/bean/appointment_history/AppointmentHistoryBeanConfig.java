package com.hospital_app.appointment_history_service.infra.config.bean.appointment_history;

import com.hospital_app.appointment_history_service.application.port.in.appointment_history.CreateAppointmentHistoryUseCase;
import com.hospital_app.appointment_history_service.application.port.out.db.appointment_history.CustomAppointmentHistoryRepository;
import com.hospital_app.appointment_history_service.application.service.appointment_history.CreateAppointmentHistoryUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppointmentHistoryBeanConfig {

    @Bean
    public CreateAppointmentHistoryUseCase createAppointmentHistoryUseCase(CustomAppointmentHistoryRepository customAppointmentHistoryRepository) {
        return new CreateAppointmentHistoryUseCaseImpl(customAppointmentHistoryRepository);
    }

}
