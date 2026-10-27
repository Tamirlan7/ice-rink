package by.tami.ticketservice.kafka.event;

import java.time.Instant;
import java.util.UUID;

public record TicketExpiredEvent(
        UUID eventId,
        Long ticketId,
        Instant occurredAt
) {}
