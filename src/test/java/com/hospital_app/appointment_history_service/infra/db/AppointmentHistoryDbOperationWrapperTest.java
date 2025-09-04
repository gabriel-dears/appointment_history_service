package com.hospital_app.appointment_history_service.infra.db;

import com.hospital_app.appointment_history_service.application.exception.AppointmentHistoryDbException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentHistoryDbOperationWrapperTest {

    @Test
    void shouldReturnOperationResultOnSuccess() {
        Integer result = AppointmentHistoryDbOperationWrapper.execute(() -> 42);
        assertEquals(42, result);
    }

    @Test
    void shouldWrapExceptionIntoDomainSpecificException() {
        RuntimeException original = new RuntimeException("DB failure");
        AppointmentHistoryDbException ex = assertThrows(
                AppointmentHistoryDbException.class,
                () -> AppointmentHistoryDbOperationWrapper.execute(() -> { throw original; })
        );
        assertTrue(ex.getMessage() != null && !ex.getMessage().isBlank());
        assertSame(original, ex.getCause());
    }
}
