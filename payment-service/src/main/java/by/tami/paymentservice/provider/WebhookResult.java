package by.tami.paymentservice.provider;

import by.tami.paymentservice.model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WebhookResult {
    private boolean signatureValid;
    private String providerPaymentId;
    private PaymentStatus newStatus;
}