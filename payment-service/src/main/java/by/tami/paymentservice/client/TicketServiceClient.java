package by.tami.paymentservice.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class TicketServiceClient {
    private final RestClient restClient;

    public void markAsPaid(Long ticketId) {
        restClient.patch()
                .uri("/tickets/{id}/status", ticketId)
                .body(Map.of("status", "PAID"))
                .retrieve()
                .toBodilessEntity();
    }
}
