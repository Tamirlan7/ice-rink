package by.tami.skateservice.service;

import by.tami.grpc.skate.ReserveSkateItem;
import by.tami.grpc.skate.ReserveSkatesReq;
import by.tami.skateservice.exception.BadRequestException;
import by.tami.skateservice.exception.ConflictException;
import by.tami.skateservice.exception.NotFoundException;
import by.tami.skateservice.model.ReservationStatus;
import by.tami.skateservice.model.Skate;
import by.tami.skateservice.model.SkateReservation;
import by.tami.skateservice.repository.SkateRepository;
import by.tami.skateservice.repository.SkateReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkateReservationService {
    private static final Duration RESERVATION_TTL = Duration.ofMinutes(15);
    private final SkateRepository skateRepository;
    private final SkateReservationRepository skateReservationRepository;

    @Transactional
    public List<Long> reserveSkates(ReserveSkatesReq request) {
        List<ReserveSkateItem> items = request.getItemsList();

        if (items.isEmpty()) {
            throw new BadRequestException("Список коньков пуст");
        }

        // одни и те же коньки дважды на один сеанс в одном запросе
        long distinctPairs = items.stream()
                .map(item -> item.getSkateId() + ":" + item.getSessionId())
                .distinct()
                .count();
        if (distinctPairs != items.size()) {
            throw new BadRequestException("Одни и те же коньки указаны дважды на один сеанс");
        }

        // уникальные id: одни коньки могут быть в запросе на разные сеансы
        Set<Long> skateIds = items.stream()
                .map(ReserveSkateItem::getSkateId)
                .collect(Collectors.toSet());

        List<Skate> skates = skateRepository.findAllById(skateIds);

        if (skates.size() != skateIds.size()) {
            throw new NotFoundException("Коньки не найдены");
        }

        if (skates.stream().anyMatch(s -> !Boolean.TRUE.equals(s.getIsAvailable()))) {
            throw new BadRequestException("Коньки не в строю");
        }

        Map<Long, Skate> skatesById = skates.stream()
                .collect(Collectors.toMap(Skate::getId, Function.identity()));

        Instant now = Instant.now();
        Instant reservedUntil = now.plus(RESERVATION_TTL);

        skateReservationRepository.expireOverdue(now);

        // идём по элементам запроса, а не по найденным конькам
        List<SkateReservation> reservations = items.stream()
                .map(item -> SkateReservation.builder()
                        .skate(skatesById.get(item.getSkateId()))
                        .sessionId(item.getSessionId())
                        .ticketId(item.getTicketId())
                        .status(ReservationStatus.RESERVED)
                        .reservedUntil(reservedUntil)
                        .build())
                .toList();

        try {
            skateReservationRepository.saveAllAndFlush(reservations);
            return items.stream().map(ReserveSkateItem::getSkateId).toList();
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Коньки уже заняты на этот сеанс");
        }
    }
}
