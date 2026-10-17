package by.tami.ticketservice.service;

import by.tami.ticketservice.client.PricingGrpcClient;
import by.tami.ticketservice.dto.PricingDto;
import by.tami.ticketservice.dto.PurchaseTicketsRequest;
import by.tami.ticketservice.dto.TicketDto;
import by.tami.ticketservice.exception.NotFoundException;
import by.tami.ticketservice.repository.TicketRepository;
import by.tami.ticketservice.dto.PurchaseTicketArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final PricingGrpcClient pricingGrpcClient;
    private TicketRepository ticketRepository;

    public TicketDto purchaseTickets(PurchaseTicketsRequest req) {
        /* fetch the session service */

        /* fetch the pricing service */
        Map<Long, PricingDto> pricings = pricingGrpcClient.getPricings();
        Set<Long> reqPricings = req.getTickets().stream()
                .map(PurchaseTicketArgs::getPricingId)
                .collect(Collectors.toSet());

        /* validate if the pricingIds they've sent are in the list */
        /* we need to fetch through the ids and
        check if there are any such pricings with those ids */
        for (Long reqPricingId : reqPricings) {
            if (reqPricingId == null || !pricings.containsKey(reqPricingId)) {
                throw new NotFoundException("Прайс для данной категории с id " + reqPricingId + " не найден");
            }
        }

        /* fetch the skate service (reserve the skates) */

        return null;
    }

}
