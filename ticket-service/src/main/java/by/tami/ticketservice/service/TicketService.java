package by.tami.ticketservice.service;

import by.tami.grpc.session.GetSessionByIdResponse;
import by.tami.grpc.session.SessionStatusProto;
import by.tami.ticketservice.client.PricingGrpcClient;
import by.tami.ticketservice.client.SessionGrpcClient;
import by.tami.ticketservice.client.SkateGrpcClient;
import by.tami.ticketservice.dto.PricingDto;
import by.tami.ticketservice.dto.PurchaseTicketsRequest;
import by.tami.ticketservice.dto.TicketDto;
import by.tami.ticketservice.exception.BadRequestException;
import by.tami.ticketservice.exception.ConflictException;
import by.tami.ticketservice.exception.NotFoundException;
import by.tami.ticketservice.mapper.TicketMapper;
import by.tami.ticketservice.model.Ticket;
import by.tami.ticketservice.model.TicketStatus;
import by.tami.ticketservice.repository.TicketRepository;
import by.tami.ticketservice.dto.PurchaseTicketArgs;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.time.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketService {

    private static final Duration TICKET_TTL = Duration.ofMinutes(15);
    private final PricingGrpcClient pricingGrpcClient;
    private final SessionGrpcClient sessionGrpcClient;
    private final SkateGrpcClient skateGrpcClient;
    private final TicketRepository ticketRepository;
    private final TransactionTemplate transactionTemplate;
    private static final ZoneId RINK_ZONE = ZoneId.of("Asia/Almaty");

    private void validateSession(GetSessionByIdResponse session, int ticketsCount) {
        if (session.getStatus() != SessionStatusProto.SESSION_STATUS_SCHEDULED) {
            throw new BadRequestException("Сеанс недоступен для покупки");
        }

        LocalDateTime start = LocalDateTime.of(
                LocalDate.of(session.getDate().getYear(), session.getDate().getMonth(), session.getDate().getDay()),
                LocalTime.of(session.getStartTime().getHours(), session.getStartTime().getMinutes())
        );

        if (start.isAfter(LocalDateTime.now())) {
            throw new BadRequestException("Сеанс уже начался");
        }

        int freePlaces = session.getAllowedPeopleQuantity() - session.getCurrentlyPeopleQuantity();

        if (ticketsCount > freePlaces) {
            throw new ConflictException("Недостаточно мест: осталось " + freePlaces);
        }
    }

    public List<TicketDto> purchaseTickets(@Valid PurchaseTicketsRequest req) {
        /* fetch the session service */
        /* inside here it's already handling the exceptions, so don't worry */
        var session = sessionGrpcClient.getSessionById(req.getSessionId());
        validateSession(session, req.getTickets().size());

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
        Instant expiresAt = Instant.now().plus(TICKET_TTL);
        /* save the tickets before reserving skates */
        List<Ticket> tickets = req.getTickets().stream()
                .map(ticketReq -> {
                    PricingDto pricing = pricings.get(ticketReq.getPricingId());
                    return Ticket.builder()
                            .userId(req.getUserId())
                            .skateId(ticketReq.getSkateId())
                            .sessionId(session.getId())
                            .status(TicketStatus.PENDING)
                            .expiresAt(expiresAt)
                            .price(BigDecimal.valueOf(pricing.getPrice()))
                            .pricingCategory(pricing.getCategory())
                            .build();
                })
                .toList();

        tickets = ticketRepository.saveAll(tickets);
        List<Long> ticketIds = tickets.stream().map(Ticket::getId).toList();

        /* reserve seats in the session */
        try {
            sessionGrpcClient.reserveSeats(session.getId(), ticketIds);
        } catch (Exception e) {
            sessionGrpcClient.releaseSeatsQuietly(ticketIds);   /* на случай, если резерв прошёл, а ответ потерялся */
            cancelTickets(tickets);
            throw e;
        }

        /*
            fetch the skate service (reserve the skates)
            after we saved (reserved) the tickets
            so we get their ids first
        * */
        try {
            skateGrpcClient.reserveSkates(tickets);
        } catch (Exception e) {
            sessionGrpcClient.releaseSeatsQuietly(ticketIds);
            cancelTickets(tickets);
            throw e;
        }

        return tickets.stream()
                .map(TicketMapper::toDto)
                .toList();
    }

    private void cancelTickets(List<Ticket> tickets) {
        List<Long> ids = tickets.stream().map(Ticket::getId).toList();
        try {
            transactionTemplate.executeWithoutResult(status ->
                    ticketRepository.updateStatus(ids, TicketStatus.PENDING, TicketStatus.CANCELLED));
        } catch (RuntimeException e) {
            // не удалось отменить (например, упала БД) — билеты всё равно истекут по TTL
            log.error("Failed to cancel tickets {} after skate reservation failure", ids, e);
        }
    }
}
