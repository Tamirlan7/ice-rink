package by.tami.sessionservice.kafka.listener;

import by.tami.sessionservice.kafka.event.TicketExpiredEvent;
import by.tami.sessionservice.service.SessionSeatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TicketEventsListener {

    private final ObjectMapper objectMapper;
    private final SessionSeatService sessionSeatService;

    @KafkaListener(topics = "ticket.expired")
    public void onTicketExpired(String message) {
        TicketExpiredEvent event = objectMapper.readValue(message, TicketExpiredEvent.class);
        sessionSeatService.releaseSeats(List.of(event.ticketId()));
        log.info("Released seats for expired ticket {}", event.ticketId());
    }

}
