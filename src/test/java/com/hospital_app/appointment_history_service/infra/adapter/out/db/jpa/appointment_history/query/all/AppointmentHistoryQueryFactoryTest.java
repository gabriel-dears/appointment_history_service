package com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history.query.all;

import com.hospital_app.appointment_history_service.application.port.in.appointment_history.AppointmentDateTimeScope;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;

class AppointmentHistoryQueryFactoryTest {

    private FutureAppointmentHistoryQueryPort future;
    private PastAppointmentHistoryQueryPort past;
    private AllAppointmentHistoryQueryPort all;

    private AppointmentHistoryQueryFactory factory;

    @BeforeEach
    void setUp() {
        future = mock(FutureAppointmentHistoryQueryPort.class);
        past = mock(PastAppointmentHistoryQueryPort.class);
        all = mock(AllAppointmentHistoryQueryPort.class);
        factory = new AppointmentHistoryQueryFactory(
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
