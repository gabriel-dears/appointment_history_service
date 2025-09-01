package com.hospital_app.appointment_history_service.infra.adapter.in.controller.graphql;

import com.hospital_app.appointment_history_service.infra.adapter.in.controller.graphql.dto.AppointmentHistoryConnection;
import com.hospital_app.appointment_history_service.infra.adapter.in.controller.graphql.dto.AppointmentHistoryWithContext;
import com.hospital_app.appointment_history_service.infra.adapter.in.controller.graphql.dto.PageInfo;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class GraphQLAppointmentHistoryController {

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
        return new AppointmentHistoryConnection(new PageInfo(0, 0, 0, 0, true, true), new ArrayList<>());
    }

    @QueryMapping
    public AppointmentHistoryConnection futureAppointmentHistoryByAppointmentId(
            @Argument UUID id,
            @Argument boolean lastVersionOnly,
            @Argument int page,
            @Argument int size
    ) {
        return new AppointmentHistoryConnection(new PageInfo(0, 0, 0, 0, true, true), new ArrayList<>());
    }

    @QueryMapping
    public AppointmentHistoryConnection allAppointmentHistoryByAppointmentId(
            @Argument UUID id,
            @Argument boolean lastVersionOnly,
            @Argument int page,
            @Argument int size
    ) {
        return new AppointmentHistoryConnection(new PageInfo(0, 0, 0, 0, true, true), new ArrayList<>());
    }

    // ========================================
    // Queries for all Appointments (with context)
    // ========================================

    @QueryMapping
    public List<AppointmentHistoryWithContext> pastAppointmentHistories(
            @Argument boolean lastVersionOnly,
            @Argument int page,
            @Argument int size
    ) {
        return new ArrayList<>();
    }

    @QueryMapping
    public List<AppointmentHistoryWithContext> futureAppointmentHistories(
            @Argument boolean lastVersionOnly,
            @Argument int page,
            @Argument int size
    ) {
        return new ArrayList<>();
    }

    @QueryMapping
    public List<AppointmentHistoryWithContext> allAppointmentHistories(
            @Argument boolean lastVersionOnly,
            @Argument int page,
            @Argument int size
    ) {
        return new ArrayList<>();
    }

}
