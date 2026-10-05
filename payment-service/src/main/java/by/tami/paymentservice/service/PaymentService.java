package by.tami.paymentservice.service;

import by.tami.paymentservice.dto.CreatePaymentRequest;
import by.tami.paymentservice.dto.CreatePaymentResponse;
import by.tami.paymentservice.dto.PaymentDto;
import by.tami.paymentservice.exception.NotFoundException;
import by.tami.paymentservice.mapper.PaymentMapper;
import by.tami.paymentservice.model.Payment;
import by.tami.paymentservice.model.PaymentProviderType;
import by.tami.paymentservice.model.PaymentStatus;
import by.tami.paymentservice.provider.CreatePaymentCommand;
import by.tami.paymentservice.provider.CreatePaymentResult;
import by.tami.paymentservice.provider.PaymentProvider;
import by.tami.paymentservice.provider.PaymentProviderRegistry;
import by.tami.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentProviderRegistry paymentProviderRegistry;

    public CreatePaymentResponse createPayment(CreatePaymentRequest request) {
        Optional<Payment> existing = paymentRepository.findByIdempotencyKey(request.getIdempotencyKey());
        if (existing.isPresent()) {
            Payment payment = existing.get();
            return new CreatePaymentResponse(PaymentMapper.toDto(payment), Map.of());
        }

        Payment newPayment = new Payment();
        newPayment.setTicketId(request.getTicketId());
        newPayment.setIdempotencyKey(request.getIdempotencyKey());
        newPayment.setProvider(request.getProvider());
        newPayment.setAmount(request.getAmount());
        newPayment.setCurrency(request.getCurrency());
        newPayment.setStatus(PaymentStatus.PENDING);

        Payment payment = paymentRepository.save(newPayment);

        CreatePaymentCommand providerCommand = new CreatePaymentCommand(
                payment.getId(),
                request.getTicketId(),
                request.getAmount(),
                request.getCurrency(),
                request.getIdempotencyKey(),
                request.getProvider()
        );

        PaymentProvider provider = paymentProviderRegistry.get(request.getProvider());
        CreatePaymentResult result = provider.createPayment(providerCommand);
        payment.setProviderPaymentId(result.getProviderPaymentId());
        payment = paymentRepository.save(payment);

        if (provider.getType() == PaymentProviderType.MOCK) {
//            markSucceeded(payment);
        }

        return new CreatePaymentResponse(PaymentMapper.toDto(payment), result.getClientPayload());
    }

    public PaymentDto getPayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment not found: " + id));
        return PaymentMapper.toDto(payment);
    }
}
