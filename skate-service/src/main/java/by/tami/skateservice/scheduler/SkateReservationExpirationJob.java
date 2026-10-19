package by.tami.skateservice.scheduler;

import by.tami.skateservice.repository.SkateReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class SkateReservationExpirationJob {

    private final SkateReservationRepository skateReservationRepository;

    @Scheduled(fixedDelay = 60_000) // раз в минуту
    @Transactional
    public void expireOverdue() {
        skateReservationRepository.expireOverdue(Instant.now());
    }

}
