package by.tami.skateservice.dto;

import by.tami.skateservice.model.Sex;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateSkateArgs {
    private Long id;

    private Long skateIdentityNumber;

    @Min(value = 15, message = "Размер не может быть установлен ниже 15")
    @Max(value = 55, message = "Размер не может быть установлен выше 55")
    private Short size;
    private Sex sex;
    private Boolean isAvailable = true;
}
