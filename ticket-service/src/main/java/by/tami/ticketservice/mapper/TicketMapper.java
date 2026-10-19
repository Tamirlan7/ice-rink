package by.tami.ticketservice.mapper;

import by.tami.ticketservice.dto.TicketDto;
import by.tami.ticketservice.model.Ticket;

public class TicketMapper {

    public static TicketDto toDto(Ticket ticket) {
        return TicketDto.builder()
                .userId(ticket.getUserId())
                .skateId(ticket.getSkateId())
                .sessionId(ticket.getSessionId())
                .price(ticket.getPrice())
                .pricingCategory(ticket.getPricingCategory())
                .expiresAt(ticket.getExpiresAt())
                .status(ticket.getStatus())
                .createdAt(ticket.getCreatedAt())
                .build();
    }

}
