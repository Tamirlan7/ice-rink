package by.tami.ticketservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "t_ticket")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "session_id", nullable = false)
    private Long sessionId;

    @Column(name = "skate_id")
    private Long skateId;  /* null — билет без коньков */

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "pricing_category", nullable = false, length = 20)
    private String pricingCategory;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @Column(name = "expires_at")
    private Instant expiresAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Builder.Default
    private Instant createdAt = Instant.now();
}
