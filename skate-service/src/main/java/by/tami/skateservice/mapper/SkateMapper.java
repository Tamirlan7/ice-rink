package by.tami.skateservice.mapper;

import by.tami.skateservice.dto.SkateDto;
import by.tami.skateservice.model.Skate;

public class SkateMapper {
    public static SkateDto toDto(Skate skate) {
        return SkateDto.builder()
                .id(skate.getId())
                .skateIdentityNumber(skate.getSkateIdentityNumber())
                .sex(skate.getSex())
                .size(skate.getSize())
                .isAvailable(skate.getIsAvailable())
                .build();
    }
}
