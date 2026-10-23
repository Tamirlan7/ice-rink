package by.tami.ticketservice.client;

import by.tami.grpc.session.*;
import by.tami.ticketservice.exception.BadRequestException;
import by.tami.ticketservice.exception.ConflictException;
import by.tami.ticketservice.exception.NotFoundException;
import by.tami.ticketservice.exception.ServiceUnavailableException;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SessionGrpcClient {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private final SessionServiceGrpc.SessionServiceBlockingStub stub;

    public void reserveSeats(Long sessionId, List<Long> ticketIds) {
        try {
            stub.withDeadlineAfter(Duration.ofSeconds(2))
                    .reserveSeats(ReserveSeatsRequest.newBuilder()
                            .setSessionId(sessionId)
                            .addAllTicketIds(ticketIds)
                            .build());
        } catch (StatusRuntimeException ex) {
            String message = ex.getStatus().getDescription();
            switch (ex.getStatus().getCode()) {
                case NOT_FOUND -> throw new NotFoundException(message);
                case INVALID_ARGUMENT -> throw new BadRequestException(message);
                case FAILED_PRECONDITION -> throw new ConflictException(message);
                case UNAVAILABLE, DEADLINE_EXCEEDED ->
                        throw new ServiceUnavailableException("session-service недоступен");
                default -> throw ex;
            }
        }
    }

    // используется при откате: ошибку не пробрасываем, чтобы не скрыть исходную
    public void releaseSeatsQuietly(List<Long> ticketIds) {
        try {
            stub.withDeadlineAfter(Duration.ofSeconds(2))
                    .releaseSeats(ReleaseSeatsRequest.newBuilder()
                            .addAllTicketIds(ticketIds)
                            .build());
        } catch (StatusRuntimeException ex) {
            log.error("Failed to release seats for tickets {}", ticketIds, ex);
        }
    }

    public GetSessionByIdResponse getSessionById(Long sessionId) {
        GetSessionByIdRequest req = GetSessionByIdRequest.newBuilder()
                .setSessionId(sessionId)
                .build();

        try {
            return stub.withDeadlineAfter(Duration.ofSeconds(2))
                    .getSessionById(req);
        } catch (StatusRuntimeException ex) {
            String message = ex.getStatus().getDescription();

            switch (ex.getStatus().getCode()) {
                case NOT_FOUND -> throw new NotFoundException(message);
                case INTERNAL -> throw new BadRequestException(message);
                case UNAVAILABLE, DEADLINE_EXCEEDED ->
                        throw new ServiceUnavailableException("session-service недоступен");
                default -> throw ex;
            }
        }
    }
}
