package com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history.query.all;

import com.hospital_app.appointment_history_service.application.port.in.appointment_history.AppointmentDateTimeScope;
import com.hospital_app.appointment_history_service.application.port.out.db.appointment_history.AppointmentHistoryQueryPort;

import java.util.Map;

public class AppointmentHistoryQueryFactory {

    private final Map<AppointmentDateTimeScope, AppointmentHistoryQueryPort> strategies;

    public AppointmentHistoryQueryFactory(
            FutureAppointmentHistoryQueryPort futureQuery,
            PastAppointmentHistoryQueryPort pastQuery,
            AllAppointmentHistoryQueryPort allQuery
    ) {
        strategies = Map.of(
                AppointmentDateTimeScope.FUTURE, futureQuery,
                AppointmentDateTimeScope.PAST, pastQuery,
                AppointmentDateTimeScope.ALL, allQuery
        );
    }

    public AppointmentHistoryQueryPort getQuery(AppointmentDateTimeScope scope) {
        return strategies.get(scope);
    }

}
