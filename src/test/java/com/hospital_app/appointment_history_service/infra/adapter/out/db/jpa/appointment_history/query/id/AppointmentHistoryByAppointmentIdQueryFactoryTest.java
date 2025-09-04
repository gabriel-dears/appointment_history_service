package com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history.query.id;

import com.hospital_app.appointment_history_service.application.port.in.appointment_history.AppointmentDateTimeScope;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;

class AppointmentHistoryByAppointmentIdQueryFactoryTest {

    private FutureAppointmentHistoryByIdQuery future;
    private PastAppointmentHistoryByIdQuery past;
    private AllAppointmentHistoryByIdQuery all;

    private AppointmentHistoryByAppointmentIdQueryFactory factory;

    @BeforeEach
    void setUp() {
        future = mock(FutureAppointmentHistoryByIdQuery.class);
        past = mock(PastAppointmentHistoryByIdQuery.class);
        all = mock(AllAppointmentHistoryByIdQuery.class);
        factory = new AppointmentHistoryByAppointmentIdQueryFactory(
                future,
                past,
                all
        );
    }

    @Test
    void shouldReturnFutureStrategyWhenScopeIsFuture() {
        assertSame(future, factory.getQuery(AppointmentDateTimeScope.FUTURE));
    }

    @Test
    void shouldReturnPastStrategyWhenScopeIsPast() {
        assertSame(past, factory.getQuery(AppointmentDateTimeScope.PAST));
    }

    @Test
    void shouldReturnAllStrategyWhenScopeIsAll() {
        assertSame(all, factory.getQuery(AppointmentDateTimeScope.ALL));
    }

}
