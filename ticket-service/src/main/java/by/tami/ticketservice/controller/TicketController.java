package by.tami.ticketservice.controller;

import by.tami.ticketservice.dto.PurchaseTicketArgs;
import by.tami.ticketservice.dto.PurchaseTicketsRequest;
import by.tami.ticketservice.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RefreshScope
@RequestMapping("/api/v1/tickets")
public class TicketController {
    private final TicketService ticketService;

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseTickets(
            @RequestBody PurchaseTicketsRequest req,
            @RequestHeader("X-User-Id") Long userId
    ) {
        req.setUserId(userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ticketService.purchaseTickets(req));
    }

}
