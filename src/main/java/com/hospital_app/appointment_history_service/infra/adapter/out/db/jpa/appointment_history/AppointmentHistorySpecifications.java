package com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history;

import org.springframework.data.jpa.domain.Specification;

import java.time.OffsetDateTime;
import java.util.UUID;

public class AppointmentHistorySpecifications {

    public static Specification<JpaAppointmentHistoryEntity> byPatientId(UUID patientId) {
        return (root, query, cb) ->
                patientId != null ? cb.equal(root.get("patientId"), patientId) : null;
    }

    public static Specification<JpaAppointmentHistoryEntity> byDoctorId(UUID doctorId) {
        return (root, query, cb) ->
                doctorId != null ? cb.equal(root.get("doctorId"), doctorId) : null;
    }

    public static Specification<JpaAppointmentHistoryEntity> byStatus(String status) {
        return (root, query, cb) ->
                (status != null && !status.isEmpty()) ? cb.equal(root.get("status"), status) : null;
    }

    public static Specification<JpaAppointmentHistoryEntity> byDoctorNameLike(String doctorName) {
        return (root, query, cb) ->
                (doctorName != null && !doctorName.isEmpty()) ?
                        cb.like(cb.lower(root.get("doctorName")), "%" + doctorName.toLowerCase() + "%") : null;
    }

    public static Specification<JpaAppointmentHistoryEntity> byPatientNameLike(String patientName) {
        return (root, query, cb) ->
                (patientName != null && !patientName.isEmpty()) ?
                        cb.like(cb.lower(root.get("patientName")), "%" + patientName.toLowerCase() + "%") : null;
    }

    public static Specification<JpaAppointmentHistoryEntity> byDateTime(OffsetDateTime dateTime) {
        return (root, query, cb) ->
                dateTime != null ? cb.equal(root.get("dateTime"), dateTime) : null;
    }

    public static Specification<JpaAppointmentHistoryEntity> dateTimeAfter(OffsetDateTime dateTime) {
        return (root, query, cb) -> {
            if (dateTime == null) return null;
            return cb.greaterThan(root.get("dateTime"), dateTime);
        };
    }

    public static Specification<JpaAppointmentHistoryEntity> dateTimeBefore(OffsetDateTime dateTime) {
        return (root, query, cb) -> {
            if (dateTime == null) return null;
            return cb.lessThan(root.get("dateTime"), dateTime);
        };
    }

}

