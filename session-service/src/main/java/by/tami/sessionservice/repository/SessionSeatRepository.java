package by.tami.sessionservice.repository;

import by.tami.sessionservice.model.SessionSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface SessionSeatRepository extends JpaRepository<SessionSeat, Long> {

    @Modifying
    @Query("delete from SessionSeat s where s.ticketId in :ticketIds")
    int deleteByTicketIds(Collection<Long> ticketIds);
}
