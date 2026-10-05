package by.tami.paymentservice.controller;

import by.tami.paymentservice.dto.CreatePaymentRequest;
import by.tami.paymentservice.dto.CreatePaymentResponse;
import by.tami.paymentservice.dto.PaymentDto;
import by.tami.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/payments")
    public ResponseEntity<CreatePaymentResponse> createPayment(
            @RequestBody CreatePaymentRequest request
    ) {
        CreatePaymentResponse response = paymentService.createPayment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/payments/{id}")
    public PaymentDto getPayment(@PathVariable Long id) {
        return paymentService.getPayment(id);
    }

}
