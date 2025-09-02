package com.hospital_app.appointment_history_service.infra.adapter.out.db.jpa.appointment_history;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

import java.time.OffsetDateTime;

@Entity
@Table(name = "appointments_history")
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
    private OffsetDateTime dateTime;  // changed

    @NotBlank
    private String status;

    @NotBlank
    private String notes;

    @NotNull
    private Long version;

    @NotNull
    private OffsetDateTime receivedAt; // changed

    @PrePersist
    public void prePersist() {
        if (receivedAt == null) {
            receivedAt = OffsetDateTime.now();  // use OffsetDateTime
        }
    }
}
