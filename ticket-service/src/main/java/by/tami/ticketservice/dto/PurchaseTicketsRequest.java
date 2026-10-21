package by.tami.ticketservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PurchaseTicketsRequest {
    @NotNull(message = "userId не должен быть null")
    private Long userId;

    @NotNull(message = "sessionId не должен быть null")
    private Long sessionId;

    @NotEmpty(message = "tickets не должен быть пустым")
    private List<@Valid PurchaseTicketArgs> tickets;
}
