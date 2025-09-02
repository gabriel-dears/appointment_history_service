package com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
                                                                           @Param("now") OffsetDateTime now, Pageable pageable);

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
                                                                                      @Param("now") OffsetDateTime now, Pageable pageable);

    Page<JpaAppointmentHistoryEntity> findByAppointmentIdOrderByVersionAsc(UUID appointmentId, Pageable pageable);

    Page<JpaAppointmentHistoryEntity> findByAppointmentIdAndDateTimeAfterOrderByVersionAsc(UUID appointmentId, OffsetDateTime now, Pageable pageable);

    Page<JpaAppointmentHistoryEntity> findByAppointmentIdAndDateTimeBeforeOrderByVersionAsc(UUID appointmentId, OffsetDateTime now, Pageable pageable);

    @Query("""
            SELECT h
                        FROM JpaAppointmentHistoryEntity h
                          WHERE h.version = (
                            SELECT MAX(h2.version)
                            FROM JpaAppointmentHistoryEntity h2
                            WHERE h2.appointmentId = h.appointmentId
                          )
            """)
    Page<JpaAppointmentHistoryEntity> findAllByLastVersion(Pageable pageable);

    @Query("""
            SELECT h
                        FROM JpaAppointmentHistoryEntity h
                          WHERE h.version = (
                            SELECT MAX(h2.version)
                            FROM JpaAppointmentHistoryEntity h2
                            WHERE h2.appointmentId = h.appointmentId
                        )
                        AND h.dateTime > :now
            """)
    Page<JpaAppointmentHistoryEntity> findAllByLastVersionFuture(OffsetDateTime now, Pageable pageable);

    @Query("""
            SELECT h
                        FROM JpaAppointmentHistoryEntity h
                          WHERE h.version = (
                            SELECT MAX(h2.version)
                            FROM JpaAppointmentHistoryEntity h2
                            WHERE h2.appointmentId = h.appointmentId
                        )
                        AND h.dateTime < :now
            """)
    Page<JpaAppointmentHistoryEntity> findAllByLastVersionPast(OffsetDateTime now, Pageable pageable);

    Page<JpaAppointmentHistoryEntity> findByDateTimeAfterOrderByVersionAsc(OffsetDateTime now, Pageable pageable);

    Page<JpaAppointmentHistoryEntity> findByDateTimeBeforeOrderByVersionAsc(OffsetDateTime now, Pageable pageable);

}
