package by.tami.skateservice.service;

import by.tami.skateservice.dto.CreateSkateArgs;
import by.tami.skateservice.dto.CreateSkateResponse;
import by.tami.skateservice.exception.BadRequestException;
import by.tami.skateservice.mapper.SkateMapper;
import by.tami.skateservice.model.Skate;
import by.tami.skateservice.repository.SkateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SkateService {

    private final SkateRepository skateRepository;

    public CreateSkateResponse createSkate(CreateSkateArgs args) {
        if (skateRepository.existsBySkateIdentityNumber(args.getSkateIdentityNumber())) {
            throw new BadRequestException("Коньки с таким идентификатором " + args.getSkateIdentityNumber() + " уже существует");
        }

        Skate skate = Skate.builder()
                .skateIdentityNumber(args.getSkateIdentityNumber())
                .size(args.getSize())
                .sex(args.getSex())
                .isAvailable(args.getIsAvailable())
                .build();

        skate = skateRepository.save(skate);
        return CreateSkateResponse.builder()
                .data(SkateMapper.toDto(skate))
                .build();
    }

}
