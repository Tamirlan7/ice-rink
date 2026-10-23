package by.tami.sessionservice.service;

import by.tami.sessionservice.exception.BadRequestException;
import by.tami.sessionservice.exception.ConflictException;
import by.tami.sessionservice.exception.NotFoundException;
import by.tami.sessionservice.model.Session;
import by.tami.sessionservice.model.SessionSeat;
import by.tami.sessionservice.model.SessionStatus;
import by.tami.sessionservice.repository.SessionRepository;
import by.tami.sessionservice.repository.SessionSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SessionSeatService {

    private final SessionSeatRepository sessionSeatRepository;
    private final SessionRepository sessionRepository;

    @Transactional
    public void reserveSeats(Long sessionId, List<Long> ticketIds) {
        if (ticketIds.isEmpty()) {
            throw new BadRequestException("Список билетов пуст");
        }

        // повторный вызов: места для этих билетов уже заняты — не занимаем второй раз
        Set<Long> alreadyReserved = sessionSeatRepository.findAllById(ticketIds).stream()
                .map(SessionSeat::getTicketId)
                .collect(Collectors.toSet());

        List<SessionSeat> newSeats = ticketIds.stream()
                .filter(id -> !alreadyReserved.contains(id))
                .map(id -> SessionSeat.builder().ticketId(id).sessionId(sessionId).build())
                .toList();

        if (newSeats.isEmpty()) {
            return;
        }

        int updated = sessionRepository.occupySeats(sessionId, newSeats.size());
        if (updated == 0) {
            explainWhyNotReserved(sessionId);
        }

        sessionSeatRepository.saveAll(newSeats);
    }

    @Transactional
    public void releaseSeats(List<Long> ticketIds) {
        // группируем по сеансу: на случай, если билеты окажутся с разных сеансов
        Map<Long, Long> countBySession = sessionSeatRepository.findAllById(ticketIds).stream()
                .collect(Collectors.groupingBy(SessionSeat::getSessionId, Collectors.counting()));

        if (countBySession.isEmpty()) {
            return;   // резерва не было или уже освободили
        }

        sessionSeatRepository.deleteByTicketIds(ticketIds);
        countBySession.forEach((sessionId, count) ->
                sessionRepository.freeSeats(sessionId, count.intValue()));
    }

    private void explainWhyNotReserved(Long sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new NotFoundException("Сеанс не найден"));

        if (session.getStatus() != SessionStatus.SCHEDULED) {
            throw new ConflictException("Сеанс недоступен для покупки");
        }
        int free = session.getAllowedPeopleQuantity() - session.getCurrentlyPeopleQuantity();
        throw new ConflictException("Недостаточно мест: осталось " + free);
    }
}
