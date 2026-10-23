package by.tami.sessionservice.service;

import by.tami.grpc.session.*;
import by.tami.sessionservice.exception.BadRequestException;
import by.tami.sessionservice.exception.ConflictException;
import by.tami.sessionservice.exception.NotFoundException;
import by.tami.sessionservice.mapper.SessionProtoMapper;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class SessionGrpcService extends SessionServiceGrpc.SessionServiceImplBase {

    private final SessionService sessionService;
    private final SessionSeatService sessionSeatService;

    @Override
    public void getSessionById(GetSessionByIdRequest request, StreamObserver<GetSessionByIdResponse> responseObserver) {
        try {
            var s = sessionService.getSessionById(request.getSessionId());
            responseObserver.onNext(SessionProtoMapper.toProto(s));
            responseObserver.onCompleted();
        } catch (NotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException());
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL.withDescription("Internal error").asRuntimeException());
        }
    }

    @Override
    public void reserveSeats(ReserveSeatsRequest request, StreamObserver<ReserveSeatsResponse> responseObserver) {
        try {
            sessionSeatService.reserveSeats(request.getSessionId(), request.getTicketIdsList());
            responseObserver.onNext(ReserveSeatsResponse.getDefaultInstance());
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

    @Override
    public void releaseSeats(ReleaseSeatsRequest request, StreamObserver<ReleaseSeatsResponse> responseObserver) {
        try {
            sessionSeatService.releaseSeats(request.getTicketIdsList());
            responseObserver.onNext(ReleaseSeatsResponse.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL.withDescription("Internal error").asRuntimeException());
        }
    }
}
