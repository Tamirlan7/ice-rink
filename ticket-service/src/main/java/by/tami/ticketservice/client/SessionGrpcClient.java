package by.tami.ticketservice.client;

import by.tami.grpc.session.GetSessionByIdRequest;
import by.tami.grpc.session.GetSessionByIdResponse;
import by.tami.grpc.session.SessionServiceGrpc;
import by.tami.ticketservice.exception.BadRequestException;
import by.tami.ticketservice.exception.NotFoundException;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class SessionGrpcClient {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private final SessionServiceGrpc.SessionServiceBlockingStub stub;

    public GetSessionByIdResponse getSessionById(Long sessionId) {
        GetSessionByIdRequest req = GetSessionByIdRequest.newBuilder()
                .setSessionId(sessionId)
                .build();

        try {
            return stub.withDeadlineAfter(Duration.ofSeconds(2))
                    .getSessionById(req);
        } catch (StatusRuntimeException ex) {
            String message = ex.getMessage();

            switch (ex.getStatus().getCode()) {
                case NOT_FOUND -> throw new NotFoundException(message);
                case INTERNAL -> throw new BadRequestException(message);
                default -> throw ex;
            }
        }
    }
}
