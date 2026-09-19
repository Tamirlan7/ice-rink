package by.tami.pricingservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreatePricingArgs {

    @Min(value = 200, message = "Минимальное значение price должна быть больше либо равно 200")
    @Positive(message = "Поле price должно быть больше 0")
    private Integer price;

    @Size(max = 255, message = "Поле category не должно быть больше 255 символов")
    @NotBlank(message = "Это поле не должна быть пустой")
    private String category;
}
