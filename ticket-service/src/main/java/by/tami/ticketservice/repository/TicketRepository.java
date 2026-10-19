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
           update Ticket t set t.status = :to
           where t.id in :ids and t.status = :from
           """)
    int updateStatus(Collection<Long> ids, TicketStatus from, TicketStatus to);
}
