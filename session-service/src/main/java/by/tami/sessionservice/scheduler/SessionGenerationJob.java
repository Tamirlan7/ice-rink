package by.tami.sessionservice.scheduler;

import by.tami.sessionservice.model.Session;
import by.tami.sessionservice.model.SessionStatus;
import by.tami.sessionservice.model.SessionTemplate;
import by.tami.sessionservice.repository.SessionRepository;
import by.tami.sessionservice.repository.SessionTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SessionGenerationJob {

    private static final int DAYS_AHEAD = 30;

    private final SessionTemplateRepository templateRepository;
    private final SessionRepository sessionRepository;

    @Scheduled(cron = "0 5 0 * * *") // каждый день в 00:05
    public void generateUpcomingSessions() {
        LocalDate today = LocalDate.now();

        for (int i = 0; i < DAYS_AHEAD; i++) {
            LocalDate date = today.plusDays(i);
            DayOfWeek dayOfWeek = date.getDayOfWeek();

            List<SessionTemplate> templates =
                    templateRepository.findByDayOfWeekAndIsActiveTrue(dayOfWeek);

            for (SessionTemplate template : templates) {
                boolean alreadyExists =
                        sessionRepository.existsByDateAndStartTime(date, template.getStartTime());

                if (alreadyExists) {
                    continue;
                }

                Session session = new Session();
                session.setTemplateId(template.getId());
                session.setDate(date);
                session.setStartTime(template.getStartTime());
                session.setEndTime(template.getEndTime());
                session.setAllowedPeopleQuantity(template.getAllowedPeopleQuantity());
                session.setCurrentlyPeopleQuantity((short) 0);
                session.setStatus(SessionStatus.SCHEDULED);

                sessionRepository.save(session);
            }
        }
    }
}