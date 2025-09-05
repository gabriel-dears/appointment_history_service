package com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "appointments_history",
        indexes = {
                @Index(name = "idx_appointment_id_version", columnList = "appointment_id, version"),
                @Index(name = "idx_appointment_id_date_time", columnList = "appointment_id, date_time"),
                @Index(name = "idx_patient_id_date_time", columnList = "patient_id, date_time"),
                @Index(name = "idx_status_date_time", columnList = "status, date_time"),
                @Index(name = "idx_received_at", columnList = "received_at")
        }
)
@Getter
@Setter
@NoArgsConstructor
public class JpaAppointmentHistoryEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    private UUID appointmentId;

    @NotNull
    private UUID patientId;

    @NotBlank
    @Email
    @Size(max = 100)
    private String patientEmail;

    @NotBlank
    @Size(min = 3, max = 255)
    private String patientName;

    @NotNull
    private UUID doctorId;

    @NotBlank
    private String doctorName;

    @NotNull
    private OffsetDateTime dateTime;

    @NotBlank
    private String status;

    @NotBlank
    private String notes;

    @NotNull
    private Long version;

    @NotNull
    private OffsetDateTime receivedAt;

    @PrePersist
    public void prePersist() {
        if (receivedAt == null) {
            receivedAt = OffsetDateTime.now();
        }
    }
}
