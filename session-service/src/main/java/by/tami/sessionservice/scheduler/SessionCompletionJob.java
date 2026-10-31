package by.tami.sessionservice.scheduler;

import by.tami.sessionservice.repository.SessionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessionCompletionJob {

    private static final ZoneId RINK_ZONE = ZoneId.of("Asia/Almaty");
    private final SessionRepository sessionRepository;

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    @Transactional
    public void completeExpiredSessions() {
        LocalDateTime now = LocalDateTime.now(RINK_ZONE);
        int completed = sessionRepository.completeExpiredSessions(
                now.toLocalDate(),
                now.toLocalTime()
        );

        if (completed > 0) {
            log.info("Completed {} expired sessions", completed);
        }
    }
}
