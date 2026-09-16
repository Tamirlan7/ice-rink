package by.tami.skateservice.repository;

import by.tami.skateservice.model.Skate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkateRepository extends JpaRepository<Skate, Long> {
}
