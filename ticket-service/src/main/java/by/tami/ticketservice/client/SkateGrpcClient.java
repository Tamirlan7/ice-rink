package by.tami.ticketservice.client;

import by.tami.grpc.skate.SkateReservationServiceGrpc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SkateGrpcClient {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private final SkateReservationServiceGrpc.SkateReservationServiceBlockingStub stub;

    public void reserveSkates(List<Long> skateIds, Long sessionId, Long ticketId) {

    }

}
