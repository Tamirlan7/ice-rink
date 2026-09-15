package by.tami.ticketservice.controller;

import by.tami.ticketservice.dto.PurchaseTicketArgs;
import by.tami.ticketservice.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private TicketService ticketService;

    @GetMapping("/test")
    public String test(
            @RequestHeader("X-User-Id") String userId
    ) {
        return "Hello user with id " + userId + "!";
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseTicket(
            @RequestBody PurchaseTicketArgs args
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ticketService.purchaseTicket(args));
    }

}
