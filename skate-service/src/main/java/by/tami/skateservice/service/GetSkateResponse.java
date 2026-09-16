package by.tami.skateservice.service;

import by.tami.skateservice.dto.SkateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class GetSkateResponse {
    private SkateDto data;
}
