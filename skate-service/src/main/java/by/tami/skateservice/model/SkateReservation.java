package by.tami.skateservice.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "t_skate_reservation")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SkateReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "skate_id", nullable = false)
    private Skate skate;

    @Column(name = "session_id", nullable = false)
    private Long sessionId;

    @Column(name = "ticket_id", nullable = false)
    private Long ticketId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Column(name = "reserved_until")
    private Instant reservedUntil;

    @Column(name = "created_at", nullable = false)
    @Builder.Default
    private Instant createdAt = Instant.now();
}
