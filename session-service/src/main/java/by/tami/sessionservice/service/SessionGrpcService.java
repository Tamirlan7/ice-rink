package by.tami.sessionservice.service;

import by.tami.grpc.session.GetSessionByIdRequest;
import by.tami.grpc.session.GetSessionByIdResponse;
import by.tami.grpc.session.SessionServiceGrpc;
import by.tami.sessionservice.exception.NotFoundException;
import by.tami.sessionservice.mapper.SessionProtoMapper;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class SessionGrpcService extends SessionServiceGrpc.SessionServiceImplBase {

    private final SessionService sessionService;

    @Override
    public void getSessionById(GetSessionByIdRequest request, StreamObserver<GetSessionByIdResponse> responseObserver) {
        try {
            var s = sessionService.getSessionById(request.getSessionId());
            responseObserver.onNext(SessionProtoMapper.toProto(s));
            responseObserver.onCompleted();
        } catch (NotFoundException e) {
            responseObserver.onError(e);
        }
    }
}
