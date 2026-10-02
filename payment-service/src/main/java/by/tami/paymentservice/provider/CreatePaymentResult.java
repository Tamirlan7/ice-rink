package by.tami.paymentservice.provider;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatePaymentResult {
    private String providerPaymentId;
    private String redirectUrl; // куда отправить пользователя вводить карту
}