package by.tami.ticketservice.service;

import by.tami.ticketservice.client.PricingGrpcClient;
import by.tami.ticketservice.dto.PurchaseTicketsRequest;
import by.tami.ticketservice.dto.TicketDto;
import by.tami.ticketservice.repository.TicketRepository;
import by.tami.ticketservice.dto.PurchaseTicketArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final PricingGrpcClient pricingGrpcClient;
    private TicketRepository ticketRepository;

    public TicketDto purchaseTickets(PurchaseTicketsRequest req) {
        /* fetch the session service */

        /* fetch the pricing service */
        var pricings = pricingGrpcClient.getPricings();

        /* fetch the skate service */

        return null;
    }

}
