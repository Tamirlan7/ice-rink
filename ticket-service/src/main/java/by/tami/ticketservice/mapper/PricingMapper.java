package by.tami.ticketservice.mapper;

import by.tami.ticketservice.dto.PricingDto;
import com.tami.grpc.pricing.PricingInfo;

public class PricingMapper {
    public static PricingDto toDto(PricingInfo pricing) {
        return PricingDto.builder()
                .id(pricing.getId())
                .price(pricing.getPrice())
                .category(pricing.getCategory())
//                .createdAt(pricing.getCreatedAt())
                .build();
    }
}

