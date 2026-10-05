package by.tami.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class CreatePaymentResponse {
    private PaymentDto payment;
    private Map<String, Object> clientPayload;
}
