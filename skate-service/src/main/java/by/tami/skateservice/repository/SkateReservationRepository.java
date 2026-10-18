package by.tami.skateservice.repository;

import by.tami.skateservice.model.SkateReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface SkateReservationRepository extends JpaRepository<SkateReservation, Long> {

    @Modifying
    @Query("""
           update SkateReservation r set r.status = ReservationStatus.EXPIRED
           where r.status = ReservationStatus.RESERVED and r.reservedUntil < :now
           """)
    int expireOverdue(Instant now);

}
