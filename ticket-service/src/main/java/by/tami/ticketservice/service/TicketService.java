package by.tami.ticketservice.service;

import by.tami.ticketservice.client.PricingGrpcClient;
import by.tami.ticketservice.client.SkateGrpcClient;
import by.tami.ticketservice.dto.PricingDto;
import by.tami.ticketservice.dto.PurchaseTicketsRequest;
import by.tami.ticketservice.dto.TicketDto;
import by.tami.ticketservice.exception.NotFoundException;
import by.tami.ticketservice.model.Ticket;
import by.tami.ticketservice.model.TicketStatus;
import by.tami.ticketservice.repository.TicketRepository;
import by.tami.ticketservice.dto.PurchaseTicketArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {

    private static final Duration TICKET_TTL = Duration.ofMinutes(15);
    private final PricingGrpcClient pricingGrpcClient;
    private final SkateGrpcClient skateGrpcClient;
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

        /* save the tickets before reserving skates */
        List<Ticket> tickets = req.getTickets().stream()
                .map(ticketReq -> Ticket.builder()
                        .userId(req.getUserId())
                        .skateId(ticketReq.getSkateId())
                        .sessionId(ticketReq.getSessionId())
                        .status(TicketStatus.PENDING)
                        .expiresAt(Instant.now().plus(TICKET_TTL))
                        .price(BigDecimal.valueOf(pricings.get(ticketReq.getPricingId()).getPrice()))
                        .pricingCategory(pricings.get(ticketReq.getPricingId()).getCategory())
                        .build())
                .toList();

        tickets = ticketRepository.saveAll(tickets);

        /*
            fetch the skate service (reserve the skates)
            after we saved (reserved) the tickets
            so we get their ids first
        * */
        skateGrpcClient.reserveSkates(tickets);

        return null;
    }

}
