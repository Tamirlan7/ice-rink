package by.tami.ticketservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PurchaseTicketsRequest {
    private Long userId;
    private List<PurchaseTicketArgs> tickets;
}
