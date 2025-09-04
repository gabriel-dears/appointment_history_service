package com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Repository
public interface JpaAppointmentHistoryRepository extends JpaRepository<JpaAppointmentHistoryEntity, UUID>, JpaSpecificationExecutor<JpaAppointmentHistoryEntity> {

    @Query("""
            SELECT h
                        FROM JpaAppointmentHistoryEntity h
                        WHERE h.appointmentId = :appointmentId
                        AND (:patientId IS NULL or h.patientId = :patientId)
                        ORDER BY h.version DESC, h.dateTime DESC
            """)
    Page<JpaAppointmentHistoryEntity> findByPatientIdAndAppointmentIdOrderByVersionAsc(@Param("patientId") UUID patientId, UUID appointmentId, Pageable pageable);

    @Query("""
            SELECT h
                        FROM JpaAppointmentHistoryEntity h
                        WHERE h.appointmentId = :appointmentId
                        AND (:patientId IS NULL or h.patientId = :patientId)
                        AND h.dateTime > :now
                        ORDER BY h.version DESC, h.dateTime DESC
            """)
    Page<JpaAppointmentHistoryEntity> findByPatientIdAndAppointmentIdAndDateTimeAfterOrderByVersionAsc(@Param("patientId") UUID patientId, UUID appointmentId, OffsetDateTime now, Pageable pageable);

    @Query("""
            SELECT h
                        FROM JpaAppointmentHistoryEntity h
                        WHERE h.appointmentId = :appointmentId
                        AND (:patientId IS NULL or h.patientId = :patientId)
                        AND h.dateTime < :now
                        ORDER BY h.version DESC, h.dateTime DESC
            """)
    Page<JpaAppointmentHistoryEntity> findByPatientIdAndAppointmentIdAndDateTimeBeforeOrderByVersionAsc(@Param("patientId") UUID patientId, UUID appointmentId, OffsetDateTime now, Pageable pageable);


    @Query("""
            SELECT h
                        FROM JpaAppointmentHistoryEntity h
                        WHERE h.appointmentId = :appointmentId
                          AND (:patientId IS NULL or h.patientId = :patientId)
                          AND h.version = (
                            SELECT MAX(h2.version)
                            FROM JpaAppointmentHistoryEntity h2
                            WHERE h2.appointmentId = h.appointmentId
                        )
            """)
    Page<JpaAppointmentHistoryEntity> findByLastVersionAppointmentId(
            @Param("patientId") UUID patientId, @Param("appointmentId") UUID appointmentId, Pageable pageable
    );

    @Query("""
            SELECT h
                        FROM JpaAppointmentHistoryEntity h
                        WHERE h.appointmentId = :appointmentId
                          AND h.dateTime > :now
                          AND (:patientId IS NULL or h.patientId = :patientId)
                          AND h.version = (
                            SELECT MAX(h2.version)
                            FROM JpaAppointmentHistoryEntity h2
                            WHERE h2.appointmentId = h.appointmentId
                        )
            """)
    Page<JpaAppointmentHistoryEntity> findByLastVersionAppointmentIdFuture(@Param("patientId") UUID patientId, @Param("appointmentId") UUID appointmentId,
                                                                           @Param("now") OffsetDateTime now, Pageable pageable);

    @Query("""
            SELECT h
                        FROM JpaAppointmentHistoryEntity h
                        WHERE h.appointmentId = :appointmentId
                          AND h.dateTime < :now
                          AND (:patientId IS NULL or h.patientId = :patientId)
                          AND h.version = (
                            SELECT MAX(h2.version)
                            FROM JpaAppointmentHistoryEntity h2
                            WHERE h2.appointmentId = h.appointmentId
                        )
            """)
    Page<JpaAppointmentHistoryEntity> findByLastVersionAppointmentIdAndDateTimeBefore(@Param("patientId") UUID patientId, @Param("appointmentId") UUID appointmentId, @Param("now") OffsetDateTime now, Pageable pageable);

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
                  AND (:patientEmail IS NULL OR LOWER(a.patient_email) LIKE LOWER(CONCAT('%', :patientEmail, '%')))
                  AND (:patientId IS NULL or a.patient_id = :patientId)
                  AND (:status IS NULL OR a.status = :status)
                  AND (a.date_time::date >= :startDate AND a.date_time::date <= :endDate)
                ORDER BY a.date_time desc
            """, nativeQuery = true)
    Page<JpaAppointmentHistoryEntity> searchAppointmentHistoriesLastVersionAll(
            @Param("patientId") UUID patientId, @Param("patientName") String patientName,
            @Param("doctorName") String doctorName,
            @Param("status") String status,
            LocalDate startDate, @Param("endDate") LocalDate endDate,
            String patientEmail, Pageable pageable
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
                  AND (:patientEmail IS NULL OR LOWER(a.patient_email) LIKE LOWER(CONCAT('%', :patientEmail, '%')))
                  AND (:patientId IS NULL or a.patient_id = :patientId)
                  AND (:status IS NULL OR a.status = :status)
                  AND (a.date_time::date >= :startDate AND a.date_time::date <= :endDate)
                  AND date_time > :now
                  ORDER BY a.date_time desc
            """, nativeQuery = true)
    Page<JpaAppointmentHistoryEntity> searchAppointmentHistoriesLastVersionFuture(
            @Param("patientId") UUID patientId, @Param("patientName") String patientName,
            @Param("doctorName") String doctorName,
            @Param("status") String status,
            @Param("now") OffsetDateTime now,
            LocalDate startDate, LocalDate endDate, String patientEmail, Pageable pageable
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
                  AND (:patientEmail IS NULL OR LOWER(a.patient_email) LIKE LOWER(CONCAT('%', :patientEmail, '%')))
                  AND (:patientId IS NULL or a.patient_id = :patientId)
                  AND (:status IS NULL OR a.status = :status)
                  AND (a.date_time::date >= :startDate AND a.date_time::date <= :endDate)
                  AND date_time < :now
                  ORDER BY a.date_time desc
            """, nativeQuery = true)
    Page<JpaAppointmentHistoryEntity> searchAppointmentHistoriesLastVersionPast(
            @Param("patientId") UUID patientId, @Param("patientName") String patientName,
            @Param("doctorName") String doctorName,
            @Param("status") String status,
            @Param("now") OffsetDateTime now,
            LocalDate startDate, LocalDate endDate, String patientEmail, Pageable pageable
    );

    @Query(value = """
            SELECT *
            FROM appointments_history a
            WHERE (:patientName IS NULL OR LOWER(a.patient_name) LIKE LOWER(CONCAT('%', :patientName, '%')))
              AND (:doctorName IS NULL OR LOWER(a.doctor_name) LIKE LOWER(CONCAT('%', :doctorName, '%')))
              AND (:patientEmail IS NULL OR LOWER(a.patient_email) LIKE LOWER(CONCAT('%', :patientEmail, '%')))
              AND (:patientId IS NULL or a.patient_id = :patientId)
              AND (:status IS NULL OR a.status = :status)
              AND (a.date_time::date >= :startDate AND a.date_time::date <= :endDate)
            ORDER BY a.version DESC, a.date_time DESC
            """,
            nativeQuery = true
    )
    Page<JpaAppointmentHistoryEntity> searchAppointmentHistoriesAll(
            @Param("patientId") UUID patientId, @Param("patientName") String patientName,
            @Param("doctorName") String doctorName,
            @Param("status") String status,
            LocalDate startDate, LocalDate endDate, String patientEmail, Pageable pageable
    );


    @Query(value = """
            SELECT *
            FROM appointments_history a
            WHERE (:patientName IS NULL OR LOWER(a.patient_name) LIKE LOWER(CONCAT('%', :patientName, '%')))
              AND (:doctorName IS NULL OR LOWER(a.doctor_name) LIKE LOWER(CONCAT('%', :doctorName, '%')))
              AND (:patientEmail IS NULL OR LOWER(a.patient_email) LIKE LOWER(CONCAT('%', :patientEmail, '%')))
              AND (:patientId IS NULL or a.patient_id = :patientId)
              AND (:status IS NULL OR a.status = :status)
              AND (a.date_time::date >= :startDate AND a.date_time::date <= :endDate)
              AND a.date_time > :now
            ORDER BY a.version DESC, a.date_time DESC
            """, nativeQuery = true)
    Page<JpaAppointmentHistoryEntity> searchAppointmentHistoriesFuture(
            @Param("patientId") UUID patientId, @Param("patientName") String patientName,
            @Param("doctorName") String doctorName,
            @Param("status") String status,
            @Param("now") OffsetDateTime now,
            LocalDate startDate, LocalDate endDate, String patientEmail, Pageable pageable
    );

    @Query(value = """
                SELECT *
            FROM appointments_history a
            WHERE (:patientName IS NULL OR LOWER(a.patient_name) LIKE LOWER(CONCAT('%', :patientName, '%')))
              AND (:doctorName IS NULL OR LOWER(a.doctor_name) LIKE LOWER(CONCAT('%', :doctorName, '%')))
              AND (:patientEmail IS NULL OR LOWER(a.patient_email) LIKE LOWER(CONCAT('%', :patientEmail, '%')))
              AND (:patientId IS NULL or a.patient_id = :patientId)
              AND (:status IS NULL OR a.status = :status)
              AND (a.date_time::date >= :startDate AND a.date_time::date <= :endDate)
              AND a.date_time < :now
            ORDER BY a.version DESC, a.date_time DESC
            """, nativeQuery = true)
    Page<JpaAppointmentHistoryEntity> searchAppointmentHistoriesPast(
            @Param("patientId") UUID patientId, @Param("patientName") String patientName,
            @Param("doctorName") String doctorName,
            @Param("status") String status,
            @Param("now") OffsetDateTime now,
            LocalDate startDate, LocalDate endDate, String patientEmail, Pageable pageable
    );

}
