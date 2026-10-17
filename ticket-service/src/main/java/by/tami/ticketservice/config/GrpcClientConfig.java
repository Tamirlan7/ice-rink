package by.tami.ticketservice.config;

import by.tami.grpc.skate.SkateReservationServiceGrpc;
import com.tami.grpc.pricing.PricingServiceGrpc;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.ImportGrpcClients;

@Configuration
@ImportGrpcClients(target = "pricing", types = PricingServiceGrpc.PricingServiceBlockingStub.class)
@ImportGrpcClients(target = "skate", types = SkateReservationServiceGrpc.SkateReservationServiceBlockingStub.class)
public class GrpcClientConfig {
}
