package by.tami.pricingservice.mapper;

import by.tami.pricingservice.dto.PricingDto;
import by.tami.pricingservice.model.Pricing;

public class PricingMapper {
    public static PricingDto toDto(Pricing pricing) {
        return PricingDto.builder()
                .id(pricing.getId())
                .price(pricing.getPrice())
                .category(pricing.getCategory())
                .createdAt(pricing.getCreatedAt())
                .build();
    }
}
