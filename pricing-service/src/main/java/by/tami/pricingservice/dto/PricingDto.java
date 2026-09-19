package by.tami.pricingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PricingDto {
    private Long id;
    private Integer price;
    private String category;
    private Instant createdAt;
}
