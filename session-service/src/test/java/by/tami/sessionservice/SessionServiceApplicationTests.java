package by.tami.sessionservice;

import by.tami.sessionservice.model.Session;
import by.tami.sessionservice.model.SessionStatus;
import by.tami.sessionservice.model.SessionTemplate;
import by.tami.sessionservice.repository.SessionRepository;
import by.tami.sessionservice.repository.SessionTemplateRepository;
import by.tami.sessionservice.scheduler.SessionGenerationJob;
import by.tami.sessionservice.service.SessionService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class SessionServiceApplicationTests {

    @Autowired
    private SessionGenerationJob sessionGenerationJob;

    @Test
    void contextLoads() {
    }

    @Test
    @Disabled
    void generateSessions() {
        sessionGenerationJob.generateUpcomingSessions();
    }

}
