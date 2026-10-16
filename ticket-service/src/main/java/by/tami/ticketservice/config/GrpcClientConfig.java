package by.tami.ticketservice.config;

import com.tami.grpc.pricing.PricingServiceGrpc;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.ImportGrpcClients;

@Configuration
@ImportGrpcClients(target = "pricing", types = PricingServiceGrpc.PricingServiceBlockingStub.class)
public class GrpcClientConfig {
}
