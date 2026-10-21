package by.tami.ticketservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PurchaseTicketArgs {
    private Long skateId;
    private Long pricingId;
}
