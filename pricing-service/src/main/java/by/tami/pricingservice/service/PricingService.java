package by.tami.pricingservice.service;

import by.tami.pricingservice.dto.*;
import by.tami.pricingservice.exception.BadRequestException;
import by.tami.pricingservice.exception.NotFoundException;
import by.tami.pricingservice.mapper.PricingMapper;
import by.tami.pricingservice.model.Pricing;
import by.tami.pricingservice.repository.PricingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PricingService {

    private final PricingRepository pricingRepository;

    public GetPricingsResponse getPricings() {
        List<Pricing> pricings = pricingRepository.findAll();
        return GetPricingsResponse.builder()
                .pricings(pricings.stream().map(PricingMapper::toDto).toList())
                .build();
    }

    public GetPricingResponse getPricingById(Long id) {
        var pricing = pricingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Прайс с таким идентификатором " + id + " не найден"));

        return GetPricingResponse.builder()
                .dto(PricingMapper.toDto(pricing))
                .build();
    }

    public CreatePricingResponse createPricing(CreatePricingArgs args) {
        if (pricingRepository.existsByCategory(args.getCategory())) {
            throw new BadRequestException("Прайс для категории " + args.getCategory() + " уже существует");
        }

        Pricing pricing = new Pricing();
        pricing.setPrice(args.getPrice());
        pricing.setCategory(args.getCategory());
        pricing = pricingRepository.save(pricing);

        return CreatePricingResponse.builder()
                .dto(PricingMapper.toDto(pricing))
                .build();
    }

    public UpdatePricingResponse updatePricing(UpdatePricingArgs args) {
        var pricing = pricingRepository.findById(args.getId())
                .orElseThrow(() -> new NotFoundException("Прайс с таким идентификатором " + args.getId() + " не найден"));

        if (!pricing.getCategory().equals(args.getCategory())) {
            if (pricingRepository.existsByCategory(args.getCategory())) {
                throw new BadRequestException("Прайс для категории " + args.getCategory() + " уже существует");
            }

            pricing.setCategory(args.getCategory());
        }

        if (!pricing.getPrice().equals(args.getPrice())) {
            pricing.setPrice(args.getPrice());
        }

        pricing = pricingRepository.save(pricing);

        return UpdatePricingResponse.builder()
                .dto(PricingMapper.toDto(pricing))
                .build();
    }

    public void deletePricing(Long id) {
        pricingRepository.deleteById(id);
    }

}
