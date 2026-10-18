package by.tami.skateservice.service;

import by.tami.grpc.skate.ReserveSkatesReq;
import by.tami.grpc.skate.ReserveSkatesRes;
import by.tami.skateservice.exception.BadRequestException;
import by.tami.skateservice.exception.NotFoundException;
import by.tami.skateservice.model.ReservationStatus;
import by.tami.skateservice.model.Skate;
import by.tami.skateservice.model.SkateReservation;
import by.tami.skateservice.repository.SkateRepository;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;
import by.tami.grpc.skate.SkateReservationServiceGrpc;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@GrpcService
@RequiredArgsConstructor
public class SkateGrpcService extends SkateReservationServiceGrpc.SkateReservationServiceImplBase {

    private final SkateRepository skateRepository;

    @Override
    public void reserveSkates(ReserveSkatesReq request, StreamObserver<ReserveSkatesRes> responseObserver) {
        List<Long> reservedSkateIds = new ArrayList<>();
        List<Skate> skates = skateRepository.findAllById(request.getSkateIdsList());

        if (skates.size() != request.getSkateIdsList().size()) {
            throw new NotFoundException("Коньки не найдены");
        }

        if (skates.stream().anyMatch(s -> !s.getIsAvailable())) {
            throw new BadRequestException("Коньки не в строю");
        }

        Map<Long, Skate> skatesById = skates.stream().collect(
                Collectors.toMap(Skate::getId, Function.identity())
        );

        List<SkateReservation> reservations = skates.stream()
                .map(skate -> SkateReservation.builder()
                        .skate(skatesById.get(skate.getId()))
                        .sessionId(request.getSessionId())
                        .ticketId(request.getTicketId())
                        .status(ReservationStatus.RELEASED)
                        .reservedUntil(Instant.now().plus(15, ChronoUnit.MINUTES))
                        .build())
                .toList();

        responseObserver.onNext(ReserveSkatesRes.newBuilder()
                .addAllSkateIds(reservedSkateIds)
                .build());
        responseObserver.onCompleted();
    }
}
