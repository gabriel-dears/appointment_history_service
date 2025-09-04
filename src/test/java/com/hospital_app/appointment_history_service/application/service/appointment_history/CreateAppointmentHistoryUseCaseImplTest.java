package com.hospital_app.appointment_history_service.application.service.appointment_history;

import com.hospital_app.appointment_history_service.application.port.out.db.appointment_history.CustomAppointmentHistoryRepository;
import com.hospital_app.appointment_history_service.domain.model.AppointmentHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class CreateAppointmentHistoryUseCaseImplTest {

    private CustomAppointmentHistoryRepository repository;
    private CreateAppointmentHistoryUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        repository = mock(CustomAppointmentHistoryRepository.class);
        useCase = new CreateAppointmentHistoryUseCaseImpl(repository);
    }

    @Test
    void shouldDelegateCreationToRepository() {
        AppointmentHistory input = new AppointmentHistory();
        AppointmentHistory saved = new AppointmentHistory();
        when(repository.create(input)).thenReturn(saved);

        AppointmentHistory result = useCase.createAppointmentHistory(input);

        assertSame(saved, result);
        verify(repository).create(input);
    }
}
