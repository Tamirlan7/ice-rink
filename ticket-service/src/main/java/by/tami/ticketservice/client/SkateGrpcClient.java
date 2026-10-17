package by.tami.ticketservice.client;

import by.tami.grpc.skate.SkateReservationServiceGrpc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SkateGrpcClient {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private final SkateReservationServiceGrpc.SkateReservationServiceBlockingStub stub;

    public void reserveSkates() {
        
    }

}
