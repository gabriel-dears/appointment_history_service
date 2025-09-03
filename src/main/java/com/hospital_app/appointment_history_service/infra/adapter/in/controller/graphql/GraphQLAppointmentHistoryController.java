package com.hospital_app.appointment_history_service.infra.adapter.in.controller.graphql;

import com.hospital_app.appointment_history_service.application.port.in.appointment_history.AppointmentDateTimeScope;
import com.hospital_app.appointment_history_service.application.port.in.appointment_history.FindAllAppointmentHistoryUseCase;
import com.hospital_app.appointment_history_service.application.port.in.appointment_history.FindByIdAppointmentHistoryUseCase;
import com.hospital_app.appointment_history_service.infra.adapter.in.controller.graphql.dto.AppointmentHistoryConnection;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.OffsetDateTime;
import java.util.UUID;

@Controller
public class GraphQLAppointmentHistoryController {

    private final FindByIdAppointmentHistoryUseCase findByIdAppointmentHistoryUseCase;
    private final FindAllAppointmentHistoryUseCase findAllAppointmentHistoryUseCase;

    public GraphQLAppointmentHistoryController(FindByIdAppointmentHistoryUseCase findByIdAppointmentHistoryUseCase, FindAllAppointmentHistoryUseCase findAllAppointmentHistoryUseCase) {
        this.findByIdAppointmentHistoryUseCase = findByIdAppointmentHistoryUseCase;
        this.findAllAppointmentHistoryUseCase = findAllAppointmentHistoryUseCase;
    }

    @QueryMapping
    public AppointmentHistoryConnection pastAppointmentHistoryByAppointmentId(
            @Argument UUID id,
            @Argument boolean lastVersionOnly,
            @Argument int page,
            @Argument int size
    ) {
        return findByIdAppointmentHistoryUseCase.execute(id, lastVersionOnly, page, size, AppointmentDateTimeScope.PAST);
    }

    @QueryMapping
    public AppointmentHistoryConnection futureAppointmentHistoryByAppointmentId(
            @Argument UUID id,
            @Argument boolean lastVersionOnly,
            @Argument int page,
            @Argument int size
    ) {
        return findByIdAppointmentHistoryUseCase.execute(id, lastVersionOnly, page, size, AppointmentDateTimeScope.FUTURE);
    }

    @QueryMapping
    public AppointmentHistoryConnection allAppointmentHistoryByAppointmentId(
            @Argument UUID id,
            @Argument boolean lastVersionOnly,
            @Argument int page,
            @Argument int size
    ) {
        return findByIdAppointmentHistoryUseCase.execute(id, lastVersionOnly, page, size, AppointmentDateTimeScope.ALL);
    }

    @QueryMapping
    public AppointmentHistoryConnection pastAppointmentHistories(
            @Argument boolean lastVersionOnly,
            @Argument int page,
            @Argument int size,
            @Argument String patientName,
            @Argument String doctorName,
            @Argument String status,
            @Argument OffsetDateTime dateTime
    ) {
        return findAllAppointmentHistoryUseCase.execute(
                lastVersionOnly,
                page,
                size,
                patientName,
                doctorName,
                status,
                dateTime,
                AppointmentDateTimeScope.PAST
        );
    }

    @QueryMapping
    public AppointmentHistoryConnection futureAppointmentHistories(
            @Argument boolean lastVersionOnly,
            @Argument int page,
            @Argument int size,
            @Argument String patientName,
            @Argument String doctorName,
            @Argument String status,
            @Argument OffsetDateTime dateTime
    ) {
        return findAllAppointmentHistoryUseCase.execute(
                lastVersionOnly,
                page,
                size,
                patientName,
                doctorName,
                status,
                dateTime,
                AppointmentDateTimeScope.FUTURE
        );
    }

    @QueryMapping
    public AppointmentHistoryConnection allAppointmentHistories(
            @Argument boolean lastVersionOnly,
            @Argument int page,
            @Argument int size,
            @Argument String patientName,
            @Argument String doctorName,
            @Argument String status,
            @Argument OffsetDateTime dateTime
    ) {
        return findAllAppointmentHistoryUseCase.execute(
                lastVersionOnly,
                page,
                size,
                patientName,
                doctorName,
                status,
                dateTime,
                AppointmentDateTimeScope.ALL
        );
    }

}
