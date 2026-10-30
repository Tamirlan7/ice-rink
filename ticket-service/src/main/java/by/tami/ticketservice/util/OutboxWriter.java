package by.tami.ticketservice.util;

import by.tami.ticketservice.model.OutboxEvent;
import by.tami.ticketservice.repository.OutboxEventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OutboxWriter {
    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;

    // MANDATORY: упадёт, если вызвать без открытой транзакции
    @Transactional(propagation = Propagation.MANDATORY)
    public void write(String topic, String key, Object event, UUID eventId) {
        try {
            outboxEventRepository.save(OutboxEvent.builder()
                    .id(eventId)
                    .topic(topic)
                    .eventKey(key)
                    .payload(objectMapper.writeValueAsString(event))
                    .build());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
