package by.tami.skateservice.repository;

import by.tami.skateservice.model.SkateReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkateReservationRepository extends JpaRepository<SkateReservation, Long> {
}
