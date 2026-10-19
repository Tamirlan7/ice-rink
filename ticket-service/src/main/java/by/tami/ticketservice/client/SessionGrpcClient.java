package by.tami.ticketservice.client;

import by.tami.grpc.session.SessionServiceGrpc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionGrpcClient {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private final SessionServiceGrpc.SessionServiceBlockingStub stub;

}
