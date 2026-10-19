package by.tami.ticketservice.dto;

import by.tami.ticketservice.model.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@Getter
@Builder
public class TicketDto {
    private Long id;
    private Long sessionId;
    private Long userId;
    private Long skateId;
    private BigDecimal price;
    private String pricingCategory;
    private Instant expiresAt;
    private TicketStatus status;
    private Instant createdAt;
}
