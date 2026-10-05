package by.tami.paymentservice.mapper;

import by.tami.paymentservice.dto.PaymentDto;
import by.tami.paymentservice.model.Payment;

public class PaymentMapper {
    public static PaymentDto toDto(Payment payment) {
        return new PaymentDto(
                payment.getId(),
                payment.getTicketId(),
                payment.getAmount(),
                payment.getCurrency(),
                payment.getStatus(),
                payment.getProvider()
        );
    }
}
