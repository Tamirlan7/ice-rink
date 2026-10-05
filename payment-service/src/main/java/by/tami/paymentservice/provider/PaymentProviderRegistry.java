package by.tami.paymentservice.provider;

import by.tami.paymentservice.model.PaymentProviderType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PaymentProviderRegistry {

    private final Map<PaymentProviderType, PaymentProvider> providers;

    public PaymentProviderRegistry(List<PaymentProvider> providers) {
        this.providers = providers.stream()
                .collect(Collectors.toMap(PaymentProvider::getType, Function.identity()));
    }

    public PaymentProvider get(PaymentProviderType type) {
        PaymentProvider provider = providers.get(type);
        if (provider == null) {
            throw new IllegalArgumentException("Нет реализации для провайдера: " + type);
        }
        return provider;
    }
}
