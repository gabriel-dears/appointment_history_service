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

    @Query(value = """
                SELECT *
                FROM appointments_history a
                WHERE a.version = (
                    SELECT MAX(a2.version)
                    FROM appointments_history a2
                    WHERE a2.appointment_Id = a.appointment_Id
                )
                  AND (:patientName IS NULL OR LOWER(a.patient_Name) LIKE LOWER(CONCAT('%', :patientName, '%')))
                  AND (:doctorName IS NULL OR LOWER(a.doctor_Name) LIKE LOWER(CONCAT('%', :doctorName, '%')))
                  AND (:status IS NULL OR a.status = :status)
                ORDER BY a.date_time DESC
            """, nativeQuery = true)
    Page<JpaAppointmentHistoryEntity> searchAppointmentHistoriesLastVersionAll(
            @Param("patientName") String patientName,
            @Param("doctorName") String doctorName,
            @Param("status") String status,
            Pageable pageable
    );

    @Query(value = """
                SELECT *
                FROM appointments_history a
                WHERE a.version = (
                    SELECT MAX(a2.version)
                    FROM appointments_history a2
                    WHERE a2.appointment_Id = a.appointment_Id
                )
                  AND (:patientName IS NULL OR LOWER(a.patient_Name) LIKE LOWER(CONCAT('%', :patientName, '%')))
                  AND (:doctorName IS NULL OR LOWER(a.doctor_Name) LIKE LOWER(CONCAT('%', :doctorName, '%')))
                  AND (:status IS NULL OR a.status = :status)
                  AND date_time > :now
                ORDER BY a.date_time DESC
            """, nativeQuery = true)
    Page<JpaAppointmentHistoryEntity> searchAppointmentHistoriesLastVersionFuture(
            @Param("patientName") String patientName,
            @Param("doctorName") String doctorName,
            @Param("status") String status,
            @Param("now") OffsetDateTime now,
            Pageable pageable
    );

    @Query(value = """
                SELECT *
                FROM appointments_history a
                WHERE a.version = (
                    SELECT MAX(a2.version)
                    FROM appointments_history a2
                    WHERE a2.appointment_Id = a.appointment_Id
                )
                  AND (:patientName IS NULL OR LOWER(a.patient_Name) LIKE LOWER(CONCAT('%', :patientName, '%')))
                  AND (:doctorName IS NULL OR LOWER(a.doctor_Name) LIKE LOWER(CONCAT('%', :doctorName, '%')))
                  AND (:status IS NULL OR a.status = :status)
                  AND date_time < :now
                ORDER BY a.date_time DESC
            """, nativeQuery = true)
    Page<JpaAppointmentHistoryEntity> searchAppointmentHistoriesLastVersionPast(
            @Param("patientName") String patientName,
            @Param("doctorName") String doctorName,
            @Param("status") String status,
            @Param("now") OffsetDateTime now,
            Pageable pageable
    );

    // TODO: fix all other operations to nativeQuery syntax
    @Query(value = """
            SELECT *
            FROM appointments_history a
            WHERE (:patientName IS NULL OR LOWER(a.patient_name) LIKE LOWER(CONCAT('%', :patientName, '%')))
              AND (:doctorName IS NULL OR LOWER(a.doctor_name) LIKE LOWER(CONCAT('%', :doctorName, '%')))
              AND (:status IS NULL OR a.status = :status)
            ORDER BY a.date_time DESC
            """,
            nativeQuery = true
    )
    Page<JpaAppointmentHistoryEntity> searchAppointmentHistoriesAll(
            @Param("patientName") String patientName,
            @Param("doctorName") String doctorName,
            @Param("status") String status,
            Pageable pageable
    );


    @Query(value = """
            SELECT *
            FROM appointments_history a
            WHERE (:patientName IS NULL OR LOWER(a.patient_name) LIKE LOWER(CONCAT('%', :patientName, '%')))
              AND (:doctorName IS NULL OR LOWER(a.doctor_name) LIKE LOWER(CONCAT('%', :doctorName, '%')))
              AND (:status IS NULL OR a.status = :status)
              AND a.date_time > :now
            ORDER BY a.date_time DESC
            """, nativeQuery = true)
    Page<JpaAppointmentHistoryEntity> searchAppointmentHistoriesFuture(
            @Param("patientName") String patientName,
            @Param("doctorName") String doctorName,
            @Param("status") String status,
            @Param("now") OffsetDateTime now,
            Pageable pageable
    );

    @Query(value = """
                SELECT *
            FROM appointments_history a
            WHERE (:patientName IS NULL OR LOWER(a.patient_name) LIKE LOWER(CONCAT('%', :patientName, '%')))
              AND (:doctorName IS NULL OR LOWER(a.doctor_name) LIKE LOWER(CONCAT('%', :doctorName, '%')))
              AND (:status IS NULL OR a.status = :status)
              AND a.date_time < :now
            ORDER BY a.date_time DESC
            """, nativeQuery = true)
    Page<JpaAppointmentHistoryEntity> searchAppointmentHistoriesPast(
            @Param("patientName") String patientName,
            @Param("doctorName") String doctorName,
            @Param("status") String status,
            @Param("now") OffsetDateTime now,
            Pageable pageable
    );

}
