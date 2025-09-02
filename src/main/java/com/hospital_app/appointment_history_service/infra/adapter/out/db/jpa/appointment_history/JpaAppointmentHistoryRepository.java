package com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

@Repository
public interface JpaAppointmentHistoryRepository extends JpaRepository<JpaAppointmentHistoryEntity, UUID> {

    @Query("""
            SELECT h
                        FROM JpaAppointmentHistoryEntity h
                        WHERE h.appointmentId = :appointmentId
                          AND h.version = (
                            SELECT MAX(h2.version)
                            FROM JpaAppointmentHistoryEntity h2
                            WHERE h2.appointmentId = h.appointmentId
                        )
            """)
    Page<JpaAppointmentHistoryEntity> findByLastVersionAppointmentId(
            @Param("appointmentId") UUID appointmentId,
            Pageable pageable
    );

    @Query("""
            SELECT h
                        FROM JpaAppointmentHistoryEntity h
                        WHERE h.appointmentId = :appointmentId
                          AND h.dateTime > :now
                          AND h.version = (
                            SELECT MAX(h2.version)
                            FROM JpaAppointmentHistoryEntity h2
                            WHERE h2.appointmentId = h.appointmentId
                        )
            """)
    Page<JpaAppointmentHistoryEntity> findByLastVersionAppointmentIdFuture(@Param("appointmentId") UUID appointmentId,
                                                                           @Param("now") LocalDateTime now, Pageable pageable);

    @Query("""
            SELECT h
                        FROM JpaAppointmentHistoryEntity h
                        WHERE h.appointmentId = :appointmentId
                          AND h.dateTime < :now
                          AND h.version = (
                            SELECT MAX(h2.version)
                            FROM JpaAppointmentHistoryEntity h2
                            WHERE h2.appointmentId = h.appointmentId
                        )
            """)
    Page<JpaAppointmentHistoryEntity> findByLastVersionAppointmentIdAndDateTimeBefore(@Param("appointmentId") UUID appointmentId,
                                                                                      @Param("now") LocalDateTime now, Pageable pageable);

    Page<JpaAppointmentHistoryEntity> findByAppointmentId(UUID appointmentId, Pageable pageable);

    Page<JpaAppointmentHistoryEntity> findByAppointmentIdAndDateTimeAfter(UUID appointmentId, OffsetDateTime now, Pageable pageable);

    Page<JpaAppointmentHistoryEntity> findByAppointmentIdAndDateTimeBefore(UUID appointmentId, OffsetDateTime now, Pageable pageable);

}
