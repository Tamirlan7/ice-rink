package by.tami.sessionservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "t_session_seat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionSeat {
    @Id
    @Column(name = "ticket_id")
    private Long ticketId;               // id приходит из ticket-service, не генерируется

    @Column(name = "session_id", nullable = false)
    private Long sessionId;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Builder.Default
    private Instant createdAt = Instant.now();
}
