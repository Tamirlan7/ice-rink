package by.tami.ticketservice.client;

import by.tami.ticketservice.dto.PricingDto;
import by.tami.ticketservice.exception.ServiceUnavailableException;
import by.tami.ticketservice.mapper.PricingMapper;
import com.tami.grpc.pricing.GetPricingsRequest;
import com.tami.grpc.pricing.PricingServiceGrpc;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PricingGrpcClient {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private final PricingServiceGrpc.PricingServiceBlockingStub pricingStub;

    public Map<Long, PricingDto> getPricings() {
        try {
            return pricingStub
                    .withDeadlineAfter(2, TimeUnit.SECONDS)
                    .getPricings(GetPricingsRequest.newBuilder().build())
                    .getPricingsList()
                    .stream().map(PricingMapper::toDto)
                    .collect(Collectors.toMap(PricingDto::getId, Function.identity()));
        } catch (StatusRuntimeException err) {
            switch (err.getStatus().getCode()) {
                case UNAVAILABLE, DEADLINE_EXCEEDED ->
                        throw new ServiceUnavailableException("pricing-service недоступен");
                case INVALID_ARGUMENT -> throw new IllegalArgumentException(err.getStatus().getDescription(), err);
                default -> throw err;
            }
        }
    }
}
