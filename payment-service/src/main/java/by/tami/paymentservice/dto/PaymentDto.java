package by.tami.paymentservice.dto;

import by.tami.paymentservice.model.PaymentProviderType;
import by.tami.paymentservice.model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class PaymentDto {
    private Long id;
    private Long ticketId;
    private BigDecimal amount;
    private String currency;
    private PaymentStatus status;
    private PaymentProviderType provider;
}
