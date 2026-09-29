package by.tami.sessionservice.repository;

import by.tami.sessionservice.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    boolean existsByDateAndStartTime(LocalDate date, LocalTime startTime);

    // атомарная резервация места — понадобится при покупке билета
    @Modifying
    @Query("""
            UPDATE Session s
            SET s.currentlyPeopleQuantity = s.currentlyPeopleQuantity + 1
            WHERE s.id = :sessionId
            AND s.currentlyPeopleQuantity < s.allowedPeopleQuantity
            """)
    int tryReserveSpot(@Param("sessionId") Long sessionId);
}
