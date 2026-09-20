package by.tami.pricingservice.repository;

import by.tami.pricingservice.model.Pricing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PricingRepository extends JpaRepository<Pricing, Long> {
    boolean existsByCategory(String category);
}
