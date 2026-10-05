package by.tami.paymentservice.provider;

import by.tami.paymentservice.model.PaymentProviderType;
import by.tami.paymentservice.model.PaymentStatus;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MockPaymentProvider implements PaymentProvider {
    @Override
    public PaymentProviderType getType() {
        return PaymentProviderType.MOCK;
    }

    @Override
    public CreatePaymentResult createPayment(CreatePaymentCommand command) {
        String providerPaymentId = "mock-" + command.getPaymentId();
        return new CreatePaymentResult(providerPaymentId, Map.of("status", "auto-succeeded"));
    }

    @Override
    public WebhookResult parseWebhook(String rawBody, Map<String, String> headers) {
        return new WebhookResult(true, "mock", PaymentStatus.SUCCEEDED);
    }
}
