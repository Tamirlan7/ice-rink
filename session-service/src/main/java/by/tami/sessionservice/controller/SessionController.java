package by.tami.sessionservice.controller;

import by.tami.sessionservice.dto.GetSessionsResponse;
import by.tami.sessionservice.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sessions")
public class SessionController {

    private final SessionService sessionService;

    @GetMapping
    public ResponseEntity<GetSessionsResponse> getSessions(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        if (date != null) {
            return ResponseEntity.ok(sessionService.getSessionsByDate(date));
        }

        LocalDate rangeFrom = from == null ? LocalDate.now() : from;
        LocalDate rangeTo = to == null ? LocalDate.now().plusDays(6) : to;

        return ResponseEntity.ok(sessionService.getSessionsByDateRange(rangeFrom, rangeTo));
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<?> cancelSession(@PathVariable("id") Long id) {
        return ResponseEntity.noContent().build();
    }

}
