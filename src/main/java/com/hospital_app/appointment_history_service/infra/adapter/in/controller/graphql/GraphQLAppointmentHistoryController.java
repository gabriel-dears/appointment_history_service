package com.hospital_app.appointment_history_service.infra.adapter.in.controller.graphql;

import com.hospital_app.appointment_history_service.application.port.in.appointment_history.AppointmentDateTimeScope;
import com.hospital_app.appointment_history_service.application.port.in.appointment_history.FindAllAppointmentHistoryUseCase;
import com.hospital_app.appointment_history_service.application.port.in.appointment_history.FindByIdAppointmentHistoryUseCase;
import com.hospital_app.appointment_history_service.infra.adapter.in.controller.graphql.dto.AppointmentHistoryConnection;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
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
            @Argument int size,
            Authentication authentication
    ) {
        UUID patientId = getPatientId(authentication);
        return findByIdAppointmentHistoryUseCase.execute(id, lastVersionOnly, page, size, patientId, AppointmentDateTimeScope.PAST);
    }

    @QueryMapping
    public AppointmentHistoryConnection futureAppointmentHistoryByAppointmentId(
            @Argument UUID id,
            @Argument boolean lastVersionOnly,
            @Argument int page,
            @Argument int size,
            Authentication authentication
    ) {
        UUID patientId = getPatientId(authentication);
        return findByIdAppointmentHistoryUseCase.execute(id, lastVersionOnly, page, size, patientId, AppointmentDateTimeScope.FUTURE);
    }

    @QueryMapping
    public AppointmentHistoryConnection allAppointmentHistoryByAppointmentId(
            @Argument UUID id,
            @Argument boolean lastVersionOnly,
            @Argument int page,
            @Argument int size,
            Authentication authentication
    ) {
        UUID patientId = getPatientId(authentication);
        return findByIdAppointmentHistoryUseCase.execute(id, lastVersionOnly, page, size, patientId, AppointmentDateTimeScope.ALL);
    }

    @QueryMapping
    public AppointmentHistoryConnection pastAppointmentHistories(
            @Argument boolean lastVersionOnly,
            @Argument int page,
            @Argument int size,
            @Argument String patientName,
            @Argument String doctorName,
            @Argument String status,
            @Argument LocalDate startDate,
            @Argument LocalDate endDate,
            @Argument String patientEmail,
            Authentication authentication
    ) {
        UUID patientId = getPatientId(authentication);
        return findAllAppointmentHistoryUseCase.execute(
                lastVersionOnly,
                page,
                size,
                patientId,
                patientName,
                doctorName,
                status,
                startDate, endDate, patientEmail, AppointmentDateTimeScope.PAST
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
            @Argument LocalDate startDate,
            @Argument LocalDate endDate,
            @Argument String patientEmail,
            Authentication authentication
    ) {
        UUID patientId = getPatientId(authentication);
        return findAllAppointmentHistoryUseCase.execute(
                lastVersionOnly,
                page,
                size,
                patientId,
                patientName,
                doctorName,
                status,
                startDate, endDate, patientEmail, AppointmentDateTimeScope.FUTURE
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
            @Argument LocalDate startDate,
            @Argument LocalDate endDate,
            @Argument String patientEmail,
            Authentication authentication
    ) {
        UUID patientId = getPatientId(authentication);
        return findAllAppointmentHistoryUseCase.execute(
                lastVersionOnly,
                page,
                size,
                patientId,
                patientName,
                doctorName,
                status,
                startDate,
                endDate,
                patientEmail,
                AppointmentDateTimeScope.ALL
        );
    }

    public UUID getPatientId(Authentication authentication) {
        var x = (Jwt) authentication.getPrincipal();
        String role = (String) x.getClaims().get("role");
        if ("PATIENT".equals(role)) {
            return UUID.fromString((String) x.getClaims().get("user_id"));
        }
        return null;
    }

}
