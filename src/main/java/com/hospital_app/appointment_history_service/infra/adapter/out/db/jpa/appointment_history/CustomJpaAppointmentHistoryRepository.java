package com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history;

import com.hospital_app.appointment_history_service.application.port.in.appointment_history.AppointmentDateTimeScope;
import com.hospital_app.appointment_history_service.application.port.out.db.appointment_history.CustomAppointmentHistoryRepository;
import com.hospital_app.appointment_history_service.domain.model.AppointmentHistory;
import com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history.query.all.AppointmentHistoryQueryFactory;
import com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history.query.id.AppointmentHistoryByAppointmentIdQueryFactory;
import com.hospital_app.appointment_history_service.infra.mapper.JpaAppointmentHistoryMapper;
import com.hospital_app.common.db.pagination.ApplicationPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
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
    public ApplicationPage<AppointmentHistory> findByAppointmentId(UUID appointmentId, boolean lastVersionOnly, int page, int size, AppointmentDateTimeScope appointmentDateTimeScope) {

        int validatedPage = Math.max(page, 0);
        int validatedSize = Math.max(size, 1);
        Pageable pageable = PageRequest.of(validatedPage, validatedSize);

        Page<JpaAppointmentHistoryEntity> result = appointmentHistoryByAppointmentIdQueryFactory.getQuery(appointmentDateTimeScope).findByAppointmentId(
                appointmentId, pageable, lastVersionOnly
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
    public ApplicationPage<AppointmentHistory> findAll(
            boolean lastVersionOnly,
            int page,
            int size,
            UUID patientId,
            UUID doctorId,
            String patientName,
            String doctorName,
            String status,
            OffsetDateTime dateTime
    ) {
        Specification<JpaAppointmentHistoryEntity> spec = Specification
                .<JpaAppointmentHistoryEntity>unrestricted()
                .and(AppointmentHistorySpecifications.byPatientId(patientId))
                .and(AppointmentHistorySpecifications.byDoctorId(doctorId))
                .and(AppointmentHistorySpecifications.byPatientNameLike(patientName))
                .and(AppointmentHistorySpecifications.byDoctorNameLike(doctorName))
                .and(AppointmentHistorySpecifications.byStatus(status))
                .and(AppointmentHistorySpecifications.byDateTime(dateTime));

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "dateTime"));
        var result = jpaAppointmentHistoryRepository.findAll(spec, pageable);

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
