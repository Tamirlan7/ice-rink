package by.tami.skateservice.service;

import by.tami.grpc.skate.ReserveSkatesReq;
import by.tami.grpc.skate.ReserveSkatesRes;
import by.tami.skateservice.repository.SkateRepository;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;
import by.tami.grpc.skate.SkateReservationServiceGrpc;

import java.util.ArrayList;
import java.util.List;

@GrpcService
@RequiredArgsConstructor
public class SkateGrpcService extends SkateReservationServiceGrpc.SkateReservationServiceImplBase {

    private final SkateRepository skateRepository;

    @Override
    public void reserveSkates(ReserveSkatesReq request, StreamObserver<ReserveSkatesRes> responseObserver) {
        List<Long> reservedSkateIds = new ArrayList<>();

        request.getSkateIdsList()
                .forEach(skateId -> {
                    /* reserve those skates */
                    reservedSkateIds.add(skateId);
                });


        responseObserver.onNext(ReserveSkatesRes.newBuilder()
                .addAllSkateIds(reservedSkateIds)
                .build());
        responseObserver.onCompleted();

    }
}
