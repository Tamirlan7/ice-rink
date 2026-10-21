package by.tami.ticketservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PurchaseTicketArgs {
    private Long skateId;

    @NotNull(message = "pricingId не должен быть null")
    private Long pricingId;
}
