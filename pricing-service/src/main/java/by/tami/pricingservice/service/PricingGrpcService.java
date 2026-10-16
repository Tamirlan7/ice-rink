package by.tami.pricingservice.service;

import com.google.protobuf.Timestamp;
import by.tami.pricingservice.repository.PricingRepository;
import com.tami.grpc.pricing.GetPricingsRequest;
import com.tami.grpc.pricing.GetPricingsResponse;
import com.tami.grpc.pricing.PricingServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;
import com.tami.grpc.pricing.PricingInfo;

import java.util.List;

@GrpcService
@RequiredArgsConstructor
public class PricingGrpcService extends PricingServiceGrpc.PricingServiceImplBase {

    private final PricingRepository pricingRepository;

    @Override
    public void getPricings(GetPricingsRequest request, StreamObserver<GetPricingsResponse> responseObserver) {
        List<PricingInfo> pricings = pricingRepository.findAll()
                .stream().map(pricing -> PricingInfo.newBuilder()
                        .setPrice(pricing.getPrice())
                        .setCategory(pricing.getCategory())
                        .setId(pricing.getId())
                        .setCreatedAt(Timestamp.newBuilder()
                                .setSeconds(pricing.getCreatedAt().getEpochSecond())
                                .setNanos(pricing.getCreatedAt().getNano())
                                .build())
                        .build()).toList();

        GetPricingsResponse response = GetPricingsResponse.newBuilder()
                .addAllPricings(pricings)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
