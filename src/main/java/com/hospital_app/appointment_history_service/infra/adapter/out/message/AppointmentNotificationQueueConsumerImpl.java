package com.hospital_app.appointment_history_service.infra.adapter.out.message;

import com.hospital_app.appointment_history_service.application.port.in.appointment_history.CreateAppointmentHistoryUseCase;
import com.hospital_app.appointment_history_service.domain.model.AppointmentHistory;
import com.hospital_app.appointment_history_service.infra.config.message.rabbitmq.RabbitMQNotificationConfig;
import com.hospital_app.appointment_history_service.infra.mapper.MessageAppointmentHistoryMapper;
import com.hospital_app.common.message.dto.AppointmentMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class AppointmentNotificationQueueConsumerImpl {

    private final CreateAppointmentHistoryUseCase  createAppointmentHistoryUseCase;
    private final MessageAppointmentHistoryMapper messageAppointmentHistoryMapper;

    public AppointmentNotificationQueueConsumerImpl(CreateAppointmentHistoryUseCase createAppointmentHistoryUseCase, MessageAppointmentHistoryMapper messageAppointmentHistoryMapper) {
        this.createAppointmentHistoryUseCase = createAppointmentHistoryUseCase;
        this.messageAppointmentHistoryMapper = messageAppointmentHistoryMapper;
    }

    @RabbitListener(queues = RabbitMQNotificationConfig.HISTORY_QUEUE)
    public void consume(AppointmentMessage appointmentMessage) {
        AppointmentHistory history = messageAppointmentHistoryMapper.toHistory(appointmentMessage);
        createAppointmentHistoryUseCase.createAppointmentHistory(history);
        // TODO: expose simple graphql interaction
        // TODO: create past appointments interaction
        // TODO: create future appointments interaction
        // TODO: create all appointments interaction

    }
}
