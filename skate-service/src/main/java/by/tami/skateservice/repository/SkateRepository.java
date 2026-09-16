package by.tami.skateservice.repository;

import by.tami.skateservice.model.Skate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SkateRepository extends JpaRepository<Skate, Long> {
    Boolean existsBySkateIdentityNumber(Long skateIdentityNumber);

    @Query("""
        SELECT s
        FROM Skate s
        WHERE :lastId IS NULL OR s.id > :lastId
        ORDER BY s.id ASC
    """)
    List<Skate> fetchNextPage(@Param("lastId") Long lastId, Pageable pageable);
}
