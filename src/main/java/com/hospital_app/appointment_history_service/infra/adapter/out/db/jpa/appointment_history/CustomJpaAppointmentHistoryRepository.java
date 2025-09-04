package com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history;

import com.hospital_app.appointment_history_service.application.port.in.appointment_history.AppointmentDateTimeScope;
import com.hospital_app.appointment_history_service.application.port.out.db.appointment_history.CustomAppointmentHistoryRepository;
import com.hospital_app.appointment_history_service.domain.model.AppointmentHistory;
import com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history.query.all.AppointmentHistoryQueryFactory;
import com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history.query.id.AppointmentHistoryByAppointmentIdQueryFactory;
import com.hospital_app.appointment_history_service.infra.mapper.JpaAppointmentHistoryMapper;
import com.hospital_app.common.db.pagination.ApplicationPage;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public class CustomJpaAppointmentHistoryRepository implements CustomAppointmentHistoryRepository {

    private final JpaAppointmentHistoryRepository jpaAppointmentHistoryRepository;
    private final JpaAppointmentHistoryMapper jpaAppointmentHistoryMapper;
    private final AppointmentHistoryByAppointmentIdQueryFactory appointmentHistoryByAppointmentIdQueryFactory;
    private final AppointmentHistoryQueryFactory appointmentHistoryQueryFactory;

    public CustomJpaAppointmentHistoryRepository(JpaAppointmentHistoryRepository jpaAppointmentHistoryRepository, JpaAppointmentHistoryMapper jpaAppointmentHistoryMapper, AppointmentHistoryByAppointmentIdQueryFactory appointmentHistoryByAppointmentIdQueryFactory, AppointmentHistoryQueryFactory appointmentHistoryQueryFactory) {
        this.jpaAppointmentHistoryRepository = jpaAppointmentHistoryRepository;
        this.jpaAppointmentHistoryMapper = jpaAppointmentHistoryMapper;
        this.appointmentHistoryByAppointmentIdQueryFactory = appointmentHistoryByAppointmentIdQueryFactory;
        this.appointmentHistoryQueryFactory = appointmentHistoryQueryFactory;
    }

    @Override
    public AppointmentHistory create(AppointmentHistory appointmentHistory) {
        JpaAppointmentHistoryEntity entity = jpaAppointmentHistoryMapper.toEntity(appointmentHistory);
        return jpaAppointmentHistoryMapper.toDomain(jpaAppointmentHistoryRepository.save(entity));
    }

    @Override
    public ApplicationPage<AppointmentHistory> findByAppointmentId(UUID appointmentId, boolean lastVersionOnly, int page, int size, UUID patientId, AppointmentDateTimeScope appointmentDateTimeScope) {
        int validatedPage = Math.max(page, 0);
        int validatedSize = Math.max(size, 1);

        Page<JpaAppointmentHistoryEntity> result = appointmentHistoryByAppointmentIdQueryFactory.getQuery(appointmentDateTimeScope).findByAppointmentId(
                appointmentId, validatedPage, validatedSize, patientId, lastVersionOnly
        );

        return new ApplicationPage<>(
                result.getNumber(),
                result.getSize(),
                result.getTotalPages(),
                result.getTotalElements(),
                result.isLast(),
                result.isFirst(),
                result.getContent().stream().map(jpaAppointmentHistoryMapper::toDomain).toList()
        );
    }

    @Override
    public ApplicationPage<AppointmentHistory> findAll(boolean lastVersionOnly, int page, int size, UUID patientId, String patientName, String doctorName, String status, LocalDate startDate, LocalDate endDate, String patientEmail, AppointmentDateTimeScope appointmentDateTimeScope) {
        int validatedPage = Math.max(page, 0);
        int validatedSize = Math.max(size, 1);

        Page<JpaAppointmentHistoryEntity> result = appointmentHistoryQueryFactory.getQuery(appointmentDateTimeScope).findAll(
                lastVersionOnly,
                validatedPage,
                validatedSize,
                patientId,
                patientName,
                doctorName,
                status,
                startDate,
                endDate,
                patientEmail
        );

        return new ApplicationPage<>(
                result.getNumber(),
                result.getSize(),
                result.getTotalPages(),
                result.getTotalElements(),
                result.isLast(),
                result.isFirst(),
                result.getContent().stream().map(jpaAppointmentHistoryMapper::toDomain).toList()
        );
    }

}
