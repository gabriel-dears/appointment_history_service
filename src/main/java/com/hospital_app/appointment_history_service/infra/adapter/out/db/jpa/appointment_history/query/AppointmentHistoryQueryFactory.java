package com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history.query;

import com.hospital_app.appointment_history_service.application.port.in.appointment_history.AppointmentDateTimeScope;
import com.hospital_app.appointment_history_service.application.port.out.db.appointment_history.AppointmentHistoryByIdQueryPort;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AppointmentHistoryQueryFactory {

    private final Map<AppointmentDateTimeScope, AppointmentHistoryByIdQueryPort> strategies;

    public AppointmentHistoryQueryFactory(
            FutureAppointmentHistoryByIdQuery futureQuery,
            PastAppointmentHistoryByIdQuery pastQuery,
            AllAppointmentHistoryByIdQuery allQuery
    ) {
        strategies = Map.of(
                AppointmentDateTimeScope.FUTURE, futureQuery,
                AppointmentDateTimeScope.PAST, pastQuery,
                AppointmentDateTimeScope.ALL, allQuery
        );
    }

    public AppointmentHistoryByIdQueryPort getQuery(AppointmentDateTimeScope scope) {
        return strategies.get(scope);
    }
}
