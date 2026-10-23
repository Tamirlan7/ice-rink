package by.tami.sessionservice.service;

import by.tami.sessionservice.dto.GetSessionsResponse;
import by.tami.sessionservice.exception.NotFoundException;
import by.tami.sessionservice.mapper.SessionMapper;
import by.tami.sessionservice.model.Session;
import by.tami.sessionservice.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    public Session getSessionById(Long id) {
        /* Long sessionId = 1; */
        return sessionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Сессия с Id " + id + " не найдена"));
    }

    public GetSessionsResponse getSessionsByDate(LocalDate date) {
        var sessions = sessionRepository.findByDateOrderByStartTime(date).stream()
                .map(SessionMapper::toDto)
                .toList();

        return new GetSessionsResponse(sessions);
    }

    public GetSessionsResponse getSessionsByDateRange(LocalDate from, LocalDate to) {
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("'from' date must not be after 'to' date");
        }
        var sessions = sessionRepository.findByDateBetweenOrderByDateAscStartTimeAsc(from, to).stream()
                .map(SessionMapper::toDto)
                .toList();
        return new GetSessionsResponse(sessions);
    }
}
