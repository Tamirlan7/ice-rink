package by.tami.sessionservice.repository;

import by.tami.sessionservice.model.SessionTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface SessionTemplateRepository extends JpaRepository<SessionTemplate, Long> {
    List<SessionTemplate> findByDayOfWeekAndIsActiveTrue(DayOfWeek dayOfWeek);
}
