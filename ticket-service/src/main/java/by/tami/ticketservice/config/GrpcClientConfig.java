package by.tami.ticketservice.config;

import by.tami.grpc.session.SessionServiceGrpc;
import by.tami.grpc.skate.SkateReservationServiceGrpc;
import com.tami.grpc.pricing.PricingServiceGrpc;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.ImportGrpcClients;

@Configuration
@ImportGrpcClients(target = "pricing", types = PricingServiceGrpc.PricingServiceBlockingStub.class)
@ImportGrpcClients(target = "skate", types = SkateReservationServiceGrpc.SkateReservationServiceBlockingStub.class)
@ImportGrpcClients(target = "session", types = SessionServiceGrpc.SessionServiceBlockingStub.class)
public class GrpcClientConfig {
}
