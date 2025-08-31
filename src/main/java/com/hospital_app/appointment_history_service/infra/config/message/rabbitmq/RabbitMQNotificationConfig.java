package com.hospital_app.appointment_history_service.infra.config.message.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQNotificationConfig {

    public static final String APPOINTMENT_EXCHANGE = "appointment.exchange";
    public static final String HISTORY_QUEUE = "history.queue";

    @Bean
    public FanoutExchange appointmentExchange() {
        return new FanoutExchange(APPOINTMENT_EXCHANGE);
    }

    @Bean
    public Queue historyQueue() {
        return QueueBuilder.durable(HISTORY_QUEUE).build();
    }

    @Bean
    public Binding bindingHistory(Queue historyQueue, FanoutExchange appointmentExchange) {
        return BindingBuilder.bind(historyQueue).to(appointmentExchange);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter messageConverter) {

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        return factory;
    }
}
