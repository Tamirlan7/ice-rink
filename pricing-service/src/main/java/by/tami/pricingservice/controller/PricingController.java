package by.tami.pricingservice.controller;

import by.tami.pricingservice.dto.*;
import by.tami.pricingservice.service.PricingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pricing")
public class PricingController {
    private final PricingService pricingService;

    @GetMapping
    public ResponseEntity<GetPricingsResponse> getPricings() {
        return ResponseEntity.ok(pricingService.getPricings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPricingResponse> getPricingById(@PathVariable Long id) {
        return ResponseEntity.ok(pricingService.getPricingById(id));
    }

    @PostMapping
    public ResponseEntity<CreatePricingResponse> createPricing(
            @RequestBody CreatePricingArgs args
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pricingService.createPricing(args));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdatePricingResponse> updatePricing(
            @PathVariable Long id,
            @RequestBody UpdatePricingArgs args
    ) {
        args.setId(id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pricingService.updatePricing(args));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePricing(@PathVariable Long id) {
        pricingService.deletePricing(id);
        return ResponseEntity.noContent().build();
    }
}
