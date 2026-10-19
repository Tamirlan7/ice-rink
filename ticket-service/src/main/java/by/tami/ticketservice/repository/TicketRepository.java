package by.tami.ticketservice.repository;

import by.tami.ticketservice.model.Ticket;
import by.tami.ticketservice.model.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Modifying
    @Query("""
            UPDATE Ticket t SET t.status = :to
            WHERE t.id in :ids AND t.status = :from
            """)
    void updateStatus(Collection<Long> ids, TicketStatus from, TicketStatus to);
}
