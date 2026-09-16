package by.tami.skateservice.dto;

import by.tami.skateservice.model.Sex;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateSkateArgs {
    @NotNull(message = "Поле skateIdentityNumber обязательно")
    private Long skateIdentityNumber;

    @Min(value = 15, message = "Размер не может быть установлен ниже 15")
    @Max(value = 55, message = "Размер не может быть установлен выше 55")
    @NotNull(message = "Поле size обязательно")
    private Short size;

    @NotNull(message = "Поле sex обязательно")
    private Sex sex;

    private Boolean isAvailable = true;
}
