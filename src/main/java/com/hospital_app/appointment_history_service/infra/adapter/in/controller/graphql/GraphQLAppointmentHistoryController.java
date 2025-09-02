package com.hospital_app.appointment_history_service.infra.adapter.in.controller.graphql;

import com.hospital_app.appointment_history_service.application.port.in.appointment_history.AppointmentDateTimeScope;
import com.hospital_app.appointment_history_service.application.port.in.appointment_history.FindByIdAppointmentHistoryUseCase;
import com.hospital_app.appointment_history_service.infra.adapter.in.controller.graphql.dto.AppointmentHistoryConnection;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.UUID;

@Controller
public class GraphQLAppointmentHistoryController {

    private final FindByIdAppointmentHistoryUseCase findByIdAppointmentHistoryUseCase;

    public GraphQLAppointmentHistoryController(FindByIdAppointmentHistoryUseCase findByIdAppointmentHistoryUseCase) {
        this.findByIdAppointmentHistoryUseCase = findByIdAppointmentHistoryUseCase;
    }

    // ========================================
    // Queries by Appointment ID
    // ========================================

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
        // TODO: sort desc by version -> stream().sort...
        return findByIdAppointmentHistoryUseCase.execute(id, lastVersionOnly, page, size, AppointmentDateTimeScope.ALL);
    }

    // ========================================
    // Queries for all Appointments (with context)
    // ========================================

    @QueryMapping
    public AppointmentHistoryConnection pastAppointmentHistories(
            @Argument boolean lastVersionOnly,
            @Argument int page,
            @Argument int size
    ) {
        // TODO: return similarly to the by ID query, but not filtering by ID
        // TODO:
        //      create some filters...
        //      most recents...
        //      (order by dateTime desc),
        //      specific dateTime,
        //      specific patientId,
        //      specific doctorId
        //      LIKE doctor name, patient name??
        //      specific status
        return null;
    }

    @QueryMapping
    public AppointmentHistoryConnection futureAppointmentHistories(
            @Argument boolean lastVersionOnly,
            @Argument int page,
            @Argument int size
    ) {
        return null;
    }

    @QueryMapping
    public AppointmentHistoryConnection allAppointmentHistories(
            @Argument boolean lastVersionOnly,
            @Argument int page,
            @Argument int size
    ) {
        return null;
    }

}
