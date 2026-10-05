package by.tami.paymentservice.provider;

import by.tami.paymentservice.model.PaymentProviderType;

import java.util.Map;

public interface PaymentProvider {
    /*
    *   At first, I thought I am gonna implement Payments through Kaspi API or Halyk API
    *   but, it can become more complicated for just a pet project
    *   because, I'll need to open so called "ИП", in order to integrate it into my project
    *   so, I decided that I'm not gonna overcomplicate things for a pet project, and at the same time not to remove everything here
    *   and added the Mock implementation, so we just mock the payments
    * */

    PaymentProviderType getType();

    CreatePaymentResult createPayment(CreatePaymentCommand command);

    WebhookResult parseWebhook(String rawBody, Map<String, String> headers);
}
