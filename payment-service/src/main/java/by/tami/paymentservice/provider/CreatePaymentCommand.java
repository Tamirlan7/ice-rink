package by.tami.paymentservice.provider;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class CreatePaymentCommand {
    private Long paymentId;   // id уже сохранённой строки Payment — нужен для построения invoiceID
    private Long ticketId;
    private BigDecimal amount;
    private String currency;
    private UUID idempotencyKey;
}