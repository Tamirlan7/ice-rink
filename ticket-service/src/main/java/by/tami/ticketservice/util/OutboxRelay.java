package by.tami.ticketservice.util;

import by.tami.ticketservice.model.OutboxEvent;
import by.tami.ticketservice.repository.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxRelay {

    private final OutboxEventRepository outboxEventRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(fixedDelay = 1000)
    @Transactional
    public void publish() {
        for (OutboxEvent event : outboxEventRepository.findTop100BySentAtIsNullOrderByCreatedAt()) {
            try {
                kafkaTemplate.send(event.getTopic(), event.getEventKey(), event.getPayload())
                        .get(5, TimeUnit.SECONDS);   // ждём подтверждения от Kafka
                event.setSentAt(Instant.now());
            } catch (Exception e) {
                log.warn("Failed to publish outbox event {}, will retry", event.getId(), e);
                break;   // не обгоняем застрявшее событие, сохраняем порядок
            }
        }
    }
}
