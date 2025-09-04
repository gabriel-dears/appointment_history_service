package com.hospital_app.appointment_history_service.application.service.appointment_history;

import com.hospital_app.appointment_history_service.application.port.in.appointment_history.AppointmentDateTimeScope;
import com.hospital_app.appointment_history_service.application.port.out.db.appointment_history.CustomAppointmentHistoryRepository;
import com.hospital_app.appointment_history_service.domain.model.AppointmentHistory;
import com.hospital_app.appointment_history_service.infra.adapter.in.controller.graphql.dto.AppointmentHistoryConnection;
import com.hospital_app.common.db.pagination.ApplicationPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class FindAllAppointmentHistoryUseCaseImplTest {

    private CustomAppointmentHistoryRepository repository;
    private FindAllAppointmentHistoryUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        repository = mock(CustomAppointmentHistoryRepository.class);
        useCase = new FindAllAppointmentHistoryUseCaseImpl(repository);
    }

    @Test
    void shouldReturnMappedConnectionFromRepositoryPage() {
        // Arrange
        ApplicationPage<AppointmentHistory> page = mock(ApplicationPage.class);
        AppointmentHistory model = new AppointmentHistory();
        model.setId(UUID.randomUUID());
        model.setAppointmentId(UUID.randomUUID());
        model.setPatientId(UUID.randomUUID());
        model.setPatientEmail("p@e.com");
        model.setPatientName("Patient");
        model.setDoctorId(UUID.randomUUID());
        model.setDoctorName("Doctor");
        model.setDateTime(LocalDateTime.of(2025, 1, 1, 12, 0));
        model.setStatus("CREATED");
        model.setNotes("n");
        model.setVersion(1L);
        model.setReceivedAt(LocalDateTime.of(2025, 1, 1, 12, 1));

        when(page.getContent()).thenReturn(List.of(model));
        when(page.getPageNumber()).thenReturn(2);
        when(page.getPageSize()).thenReturn(20);
        when(page.getTotalPages()).thenReturn(10);
        when(page.getTotalElements()).thenReturn(200L);
        when(page.isFirst()).thenReturn(false);
        when(page.isLast()).thenReturn(false);

        when(repository.findAll(anyBoolean(), anyInt(), anyInt(), any(), any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(page);

        // Act
        AppointmentHistoryConnection conn = useCase.execute(
                true,
                2,
                20,
                UUID.randomUUID(),
                "Pat",
                "Doc",
                "CREATED",
                LocalDate.of(2025,1,1),
                LocalDate.of(2025,12,31),
                "p@e.com",
                AppointmentDateTimeScope.ALL
        );

        // Assert
        assertNotNull(conn);
        assertEquals(2, conn.pageInfo().pageNumber());
        assertEquals(20, conn.pageInfo().pageSize());
        assertEquals(10, conn.pageInfo().totalPages());
        assertEquals(200L, conn.pageInfo().totalElements());
        assertFalse(conn.pageInfo().isFirst());
        assertFalse(conn.pageInfo().isLast());
        assertEquals(1, conn.content().size());
        assertEquals(model.getId(), conn.content().get(0).id());
        assertEquals(model.getAppointmentId(), conn.content().get(0).appointmentId());
        assertEquals(model.getPatientId(), conn.content().get(0).patientId());
        assertEquals("CREATED", conn.content().get(0).status());

        verify(repository).findAll(anyBoolean(), anyInt(), anyInt(), any(), any(), any(), any(), any(), any(), any(), any());
    }
}
