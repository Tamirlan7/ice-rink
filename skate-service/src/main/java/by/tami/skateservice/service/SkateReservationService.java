package by.tami.skateservice.service;

import by.tami.grpc.skate.ReserveSkatesReq;
import by.tami.skateservice.exception.BadRequestException;
import by.tami.skateservice.exception.NotFoundException;
import by.tami.skateservice.model.ReservationStatus;
import by.tami.skateservice.model.Skate;
import by.tami.skateservice.model.SkateReservation;
import by.tami.skateservice.repository.SkateRepository;
import by.tami.skateservice.repository.SkateReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkateReservationService {

    private final SkateRepository skateRepository;
    private final SkateReservationRepository skateReservationRepository;

    public List<Long> reserveSkates(ReserveSkatesReq request) {
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
                        .status(ReservationStatus.RESERVED)
                        .reservedUntil(Instant.now().plus(15, ChronoUnit.MINUTES))
                        .build())
                .toList();

        skateReservationRepository.saveAllAndFlush(reservations);
        return request.getSkateIdsList();
    }

}
