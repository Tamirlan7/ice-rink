package by.tami.paymentservice.dto;

import by.tami.paymentservice.model.PaymentProviderType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class CreatePaymentRequest {
    private Long ticketId;
    private BigDecimal amount;
    private String currency;
    private UUID idempotencyKey;
    private PaymentProviderType provider;
}
