package by.tami.paymentservice.repository;

import by.tami.paymentservice.model.Payment;
import by.tami.paymentservice.model.PaymentProviderType;
import org.hibernate.internal.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByIdempotencyKey(UUID idempotencyKey);

    Optional<Payment> findByProviderAndProviderPaymentId(PaymentProviderType provider, String providerPaymentId);

}
