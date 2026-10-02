package by.tami.paymentservice.provider;

import by.tami.paymentservice.model.PaymentProviderType;

import java.util.Map;

public interface PaymentProvider {
    PaymentProviderType getType();

    CreatePaymentResult createPayment(CreatePaymentCommand command);

    WebhookResult parseWebhook(String rawBody, Map<String, String> headers);
}
