package com.example.Librarymanagementsystem.model;

import com.example.Librarymanagementsystem.model.audit.DateAudit;
import com.example.Librarymanagementsystem.model.enums.ReservationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "reservations")
public class Reservation extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Unique identifier for the reservation", example = "1")
    private Long id;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id", nullable = false)
    @Schema(description = "The book associated with the reservation")
    private Book book;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    @Schema(description = "The user who made the reservation")
    private User user;

    @Getter
    @Setter
    @Column(name = "status")
    @Schema(description = "Current status of the reservation", example = "PENDING", allowableValues = {"PENDING",
            "APPROVED",
            "REJECTED"}, nullable = true, defaultValue = "PENDING")
    private ReservationStatus status = ReservationStatus.PENDING;
}
