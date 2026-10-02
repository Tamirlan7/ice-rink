package by.tami.paymentservice.provider;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class CreatePaymentResult {
    private String providerPaymentId;
    private Map<String, Object> clientPayload;
}