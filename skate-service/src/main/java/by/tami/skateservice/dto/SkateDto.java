package by.tami.skateservice.dto;

import by.tami.skateservice.model.Sex;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SkateDto {
    private Long id;
    private Long skateIdentityNumber;
    private Short size;
    private Sex sex;
    private Boolean isAvailable;
}
