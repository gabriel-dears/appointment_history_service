package com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history;

import com.hospital_app.appointment_history_service.application.port.out.db.appointment_history.CustomAppointmentHistoryRepository;
import com.hospital_app.appointment_history_service.domain.model.AppointmentHistory;
import com.hospital_app.appointment_history_service.infra.mapper.JpaAppointmentHistoryMapper;
import com.hospital_app.common.db.DbOperationWrapper;
import org.springframework.stereotype.Repository;

@Repository
public class CustomJpaAppointmentHistoryRepository implements CustomAppointmentHistoryRepository {

    private final JpaAppointmentHistoryRepository jpaAppointmentHistoryRepository;
    private final JpaAppointmentHistoryMapper jpaAppointmentHistoryMapper;

    public CustomJpaAppointmentHistoryRepository(JpaAppointmentHistoryRepository jpaAppointmentHistoryRepository, JpaAppointmentHistoryMapper jpaAppointmentHistoryMapper) {
        this.jpaAppointmentHistoryRepository = jpaAppointmentHistoryRepository;
        this.jpaAppointmentHistoryMapper = jpaAppointmentHistoryMapper;
    }

    @Override
    public AppointmentHistory create(AppointmentHistory appointmentHistory) {
        JpaAppointmentHistoryEntity entity = jpaAppointmentHistoryMapper.toEntity(appointmentHistory);
        return jpaAppointmentHistoryMapper.toDomain(jpaAppointmentHistoryRepository.save(entity));
    }

}
