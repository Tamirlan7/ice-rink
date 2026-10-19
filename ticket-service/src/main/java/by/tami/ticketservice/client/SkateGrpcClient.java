package by.tami.ticketservice.client;

import by.tami.grpc.skate.ReserveSkateItem;
import by.tami.grpc.skate.ReserveSkatesReq;
import by.tami.grpc.skate.SkateReservationServiceGrpc;
import by.tami.ticketservice.exception.BadRequestException;
import by.tami.ticketservice.exception.ConflictException;
import by.tami.ticketservice.exception.NotFoundException;
import by.tami.ticketservice.exception.ServiceUnavailableException;
import by.tami.ticketservice.model.Ticket;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SkateGrpcClient {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private final SkateReservationServiceGrpc.SkateReservationServiceBlockingStub stub;

    public void reserveSkates(List<Ticket> tickets) {
        List<ReserveSkateItem> items = tickets.stream()
                .filter(ticket -> ticket.getSkateId() != null)   // билеты без коньков пропускаем
                .map(ticket -> ReserveSkateItem.newBuilder()
                        .setSkateId(ticket.getSkateId())
                        .setSessionId(ticket.getSessionId())
                        .setTicketId(ticket.getId())
                        .build())
                .toList();

        if (items.isEmpty()) {
            return;   // никто не берёт коньки — skate-service не дёргаем
        }

        try {
            stub.withDeadlineAfter(Duration.ofSeconds(2))
                    .reserveSkates(ReserveSkatesReq.newBuilder()
                            .addAllItems(items)
                            .build());

        } catch (StatusRuntimeException e) {
            String message = e.getStatus().getDescription();
            switch (e.getStatus().getCode()) {
                case NOT_FOUND -> throw new NotFoundException(message);
                case INVALID_ARGUMENT -> throw new BadRequestException(message);
                case FAILED_PRECONDITION -> throw new ConflictException(message);
                case UNAVAILABLE, DEADLINE_EXCEEDED ->
                        throw new ServiceUnavailableException("skate-service недоступен", e);
                default -> throw e;
            }
        }
    }

}
