package by.tami.ticketservice.config;

import by.tami.ticketservice.job.TicketExpirationJob;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicsConfig {

    @Bean
    public NewTopic ticketExpiredTopic() {
        return TopicBuilder.name(TicketExpirationJob.TICKET_EXPIRED_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }

}
