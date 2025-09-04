package com.hospital_app.appointment_history_service.application.service.appointment_history;

import com.hospital_app.appointment_history_service.application.port.in.appointment_history.AppointmentDateTimeScope;
import com.hospital_app.appointment_history_service.application.port.out.db.appointment_history.CustomAppointmentHistoryRepository;
import com.hospital_app.appointment_history_service.domain.model.AppointmentHistory;
import com.hospital_app.appointment_history_service.infra.adapter.in.controller.graphql.dto.AppointmentHistoryConnection;
import com.hospital_app.common.db.pagination.ApplicationPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class FindByIdAppointmentHistoryUseCaseImplTest {

    private CustomAppointmentHistoryRepository repository;
    private FindByIdAppointmentHistoryUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        repository = mock(CustomAppointmentHistoryRepository.class);
        useCase = new FindByIdAppointmentHistoryUseCaseImpl(repository);
    }

    @Test
    void shouldReturnMappedConnectionFromRepositoryPage() {
        // Arrange
        ApplicationPage<AppointmentHistory> page = mock(ApplicationPage.class);
        AppointmentHistory model = new AppointmentHistory();
        model.setId(UUID.randomUUID());
        model.setAppointmentId(UUID.randomUUID());
        model.setPatientId(UUID.randomUUID());
        model.setDateTime(LocalDateTime.of(2025, 1, 1, 12, 0));
        model.setReceivedAt(LocalDateTime.of(2025, 1, 1, 12, 1));

        when(page.getContent()).thenReturn(List.of(model));
        when(page.getPageNumber()).thenReturn(0);
        when(page.getPageSize()).thenReturn(5);
        when(page.getTotalPages()).thenReturn(1);
        when(page.getTotalElements()).thenReturn(1L);
        when(page.isFirst()).thenReturn(true);
        when(page.isLast()).thenReturn(true);

        when(repository.findByAppointmentId(any(), anyBoolean(), anyInt(), anyInt(), any(), any())).thenReturn(page);

        // Act
        AppointmentHistoryConnection conn = useCase.execute(
                UUID.randomUUID(),
                true,
                0,
                5,
                UUID.randomUUID(),
                AppointmentDateTimeScope.PAST
        );

        // Assert
        assertNotNull(conn);
        assertEquals(1, conn.content().size());
        assertEquals(0, conn.pageInfo().pageNumber());
        assertEquals(5, conn.pageInfo().pageSize());
        assertTrue(conn.pageInfo().isFirst());
        assertTrue(conn.pageInfo().isLast());

        verify(repository).findByAppointmentId(any(), anyBoolean(), anyInt(), anyInt(), any(), any());
    }
}
