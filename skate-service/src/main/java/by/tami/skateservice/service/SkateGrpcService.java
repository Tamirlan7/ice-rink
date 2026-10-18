package by.tami.skateservice.service;

import by.tami.grpc.skate.ReserveSkatesReq;
import by.tami.grpc.skate.ReserveSkatesRes;
import by.tami.skateservice.exception.BadRequestException;
import by.tami.skateservice.exception.ConflictException;
import by.tami.skateservice.exception.NotFoundException;
import by.tami.skateservice.model.ReservationStatus;
import by.tami.skateservice.model.Skate;
import by.tami.skateservice.model.SkateReservation;
import by.tami.skateservice.repository.SkateRepository;
import by.tami.skateservice.repository.SkateReservationRepository;
import io.grpc.Status;
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


    private final SkateReservationService skateReservationService;

    @Override
    public void reserveSkates(ReserveSkatesReq request, StreamObserver<ReserveSkatesRes> responseObserver) {
        try {
            List<Long> reservationIds = skateReservationService.reserveSkates(request);

            responseObserver.onNext(ReserveSkatesRes.newBuilder()
                    .addAllSkateIds(reservationIds)
                    .build());
            responseObserver.onCompleted();

        } catch (NotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException());
        } catch (BadRequestException e) {
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription(e.getMessage()).asRuntimeException());
        } catch (ConflictException e) {
            responseObserver.onError(Status.FAILED_PRECONDITION.withDescription(e.getMessage()).asRuntimeException());
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL.withDescription("Internal error").asRuntimeException());
        }
    }
}
