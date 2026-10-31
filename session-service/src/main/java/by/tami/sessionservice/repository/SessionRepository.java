package by.tami.sessionservice.repository;

import by.tami.sessionservice.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    boolean existsByDateAndStartTime(LocalDate date, LocalTime startTime);

    List<Session> findByDateOrderByStartTime(LocalDate date);

    List<Session> findByDateBetweenOrderByDateAscStartTimeAsc(LocalDate from, LocalDate to);

    @Modifying
    @Query("""
        UPDATE Session s
        SET s.status = SessionStatus.COMPLETED
        WHERE s.status = SessionStatus.SCHEDULED
        AND (s.date < :today OR (s.date = :today AND s.endTime < :nowTime))
    """)
    int completeExpiredSessions(LocalDate today, LocalTime nowTime);

    // атомарная резервация места — понадобится при покупке билета
    @Modifying
    @Query("""
            UPDATE Session s
            SET s.currentlyPeopleQuantity = s.currentlyPeopleQuantity + 1
            WHERE s.id = :sessionId
            AND s.currentlyPeopleQuantity < s.allowedPeopleQuantity
            """)
    int tryReserveSpot(@Param("sessionId") Long sessionId);

    // занять места: сработает, только если сеанс SCHEDULED и мест хватает
    @Modifying(clearAutomatically = true)
    @Query(value = """
            UPDATE t_session
            SET currently_people_quantity = currently_people_quantity + :count
            WHERE id = :sessionId
              AND status = 'SCHEDULED'
              AND currently_people_quantity + :count <= allowed_people_quantity
            """, nativeQuery = true)
    int occupySeats(Long sessionId, int count);

    // освободить места: не даём уйти в минус
    @Modifying(clearAutomatically = true)
    @Query(value = """
            UPDATE t_session
            SET currently_people_quantity = GREATEST(currently_people_quantity - :count, 0)
            WHERE id = :sessionId
            """, nativeQuery = true)
    int freeSeats(Long sessionId, int count);

}
