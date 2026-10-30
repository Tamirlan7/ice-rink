package by.tami.ticketservice.job;

import by.tami.ticketservice.kafka.event.TicketExpiredEvent;
import by.tami.ticketservice.model.Ticket;
import by.tami.ticketservice.model.TicketStatus;
import by.tami.ticketservice.repository.TicketRepository;
import by.tami.ticketservice.util.OutboxWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class TicketExpirationJob {

    public static final String TICKET_EXPIRED_TOPIC = "ticket.expired";

    private final TicketRepository ticketRepository;
    private final OutboxWriter outboxWriter;

    @Scheduled(fixedDelay = 60_000)
    @Transactional
    public void expireOverdueTickets() {
        List<Ticket> expired = ticketRepository
                .findTop100ByStatusAndExpiresAtBefore(TicketStatus.PENDING, Instant.now());

        for (Ticket ticket : expired) {
            ticket.setStatus(TicketStatus.EXPIRED);

            UUID eventId = UUID.randomUUID();
            outboxWriter.write(
                    TICKET_EXPIRED_TOPIC,
                    ticket.getId().toString(),
                    new TicketExpiredEvent(eventId, ticket.getId(), Instant.now()),
                    eventId);
        }

        if (!expired.isEmpty()) {
            log.info("Expired {} tickets", expired.size());
        }
    }
}