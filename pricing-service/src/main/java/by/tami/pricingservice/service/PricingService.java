package by.tami.pricingservice.service;

import by.tami.pricingservice.dto.*;
import by.tami.pricingservice.repository.PricingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PricingService {

    private final PricingRepository pricingRepository;

    public GetPricingsResponse getPricings() {
        return null;
    }

    public GetPricingResponse getPricingById(Long id) {
        return null;
    }

    public CreatePricingResponse createPricing(CreatePricingArgs args) {
        return null;
    }

    public UpdatePricingResponse updatePricing(UpdatePricingArgs args) {
        return null;
    }

    public void deletePricing(Long id) {
        pricingRepository.deleteById(id);
    }

}
