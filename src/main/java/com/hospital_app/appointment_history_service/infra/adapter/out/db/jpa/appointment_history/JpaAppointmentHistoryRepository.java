package com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.UUID;

@Repository
public interface JpaAppointmentHistoryRepository extends JpaRepository<JpaAppointmentHistoryEntity, UUID>, JpaSpecificationExecutor<JpaAppointmentHistoryEntity> {

    Page<JpaAppointmentHistoryEntity> findByAppointmentIdOrderByVersionAsc(UUID appointmentId, Pageable pageable);

    Page<JpaAppointmentHistoryEntity> findByAppointmentIdAndDateTimeAfterOrderByVersionAsc(UUID appointmentId, OffsetDateTime now, Pageable pageable);

    Page<JpaAppointmentHistoryEntity> findByAppointmentIdAndDateTimeBeforeOrderByVersionAsc(UUID appointmentId, OffsetDateTime now, Pageable pageable);

    Page<JpaAppointmentHistoryEntity> findByDateTimeAfterOrderByVersionAsc(OffsetDateTime now, Pageable pageable);

    Page<JpaAppointmentHistoryEntity> findByDateTimeBeforeOrderByVersionAsc(OffsetDateTime now, Pageable pageable);

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

    @Query("""
                SELECT a
                FROM JpaAppointmentHistoryEntity a
                WHERE a.version = (
                    SELECT MAX(a2.version)
                    FROM JpaAppointmentHistoryEntity a2
                    WHERE a2.appointmentId = a.appointmentId
                )
                AND (:patientId IS NULL OR a.patientId = :patientId)
                AND (:doctorId IS NULL OR a.doctorId = :doctorId)
                AND (:patientName IS NULL OR LOWER(a.patientName) LIKE LOWER(CONCAT('%', :patientName, '%')))
                AND (:doctorName IS NULL OR LOWER(a.doctorName) LIKE LOWER(CONCAT('%', :doctorName, '%')))
                AND (:status IS NULL OR a.status = :status)
                AND (:dateTime IS NULL OR a.dateTime = :dateTime)
                ORDER BY a.dateTime DESC
            """)
    Page<JpaAppointmentHistoryEntity> searchAppointmentHistoriesLastVersionAll(
            @Param("patientId") UUID patientId,
            @Param("doctorId") UUID doctorId,
            @Param("patientName") String patientName,
            @Param("doctorName") String doctorName,
            @Param("status") String status,
            @Param("dateTime") OffsetDateTime dateTime,
            Pageable pageable
    );

    @Query("""
                SELECT a
                FROM JpaAppointmentHistoryEntity a
                WHERE a.version = (
                    SELECT MAX(a2.version)
                    FROM JpaAppointmentHistoryEntity a2
                    WHERE a2.appointmentId = a.appointmentId
                )
                AND (:patientId IS NULL OR a.patientId = :patientId)
                AND (:doctorId IS NULL OR a.doctorId = :doctorId)
                AND (:patientName IS NULL OR LOWER(a.patientName) LIKE LOWER(CONCAT('%', :patientName, '%')))
                AND (:doctorName IS NULL OR LOWER(a.doctorName) LIKE LOWER(CONCAT('%', :doctorName, '%')))
                AND (:status IS NULL OR a.status = :status)
                AND (:dateTime IS NULL OR a.dateTime = :dateTime)
                AND a.dateTime > :now
                ORDER BY a.dateTime DESC
            """)
    Page<JpaAppointmentHistoryEntity> searchAppointmentHistoriesLastVersionFuture(
            @Param("patientId") UUID patientId,
            @Param("doctorId") UUID doctorId,
            @Param("patientName") String patientName,
            @Param("doctorName") String doctorName,
            @Param("status") String status,
            @Param("dateTime") OffsetDateTime dateTime,
            @Param("now") OffsetDateTime now,
            Pageable pageable
    );

    @Query("""
                SELECT a
                FROM JpaAppointmentHistoryEntity a
                WHERE a.version = (
                    SELECT MAX(a2.version)
                    FROM JpaAppointmentHistoryEntity a2
                    WHERE a2.appointmentId = a.appointmentId
                )
                AND (:patientId IS NULL OR a.patientId = :patientId)
                AND (:doctorId IS NULL OR a.doctorId = :doctorId)
                AND (:patientName IS NULL OR LOWER(a.patientName) LIKE LOWER(CONCAT('%', :patientName, '%')))
                AND (:doctorName IS NULL OR LOWER(a.doctorName) LIKE LOWER(CONCAT('%', :doctorName, '%')))
                AND (:status IS NULL OR a.status = :status)
                AND (:dateTime IS NULL OR a.dateTime = :dateTime)
                AND a.dateTime < :now
                ORDER BY a.dateTime DESC
            """)
    Page<JpaAppointmentHistoryEntity> searchAppointmentHistoriesLastVersionPast(
            @Param("patientId") UUID patientId,
            @Param("doctorId") UUID doctorId,
            @Param("patientName") String patientName,
            @Param("doctorName") String doctorName,
            @Param("status") String status,
            @Param("dateTime") OffsetDateTime dateTime,
            @Param("now") OffsetDateTime now,
            Pageable pageable
    );

}
