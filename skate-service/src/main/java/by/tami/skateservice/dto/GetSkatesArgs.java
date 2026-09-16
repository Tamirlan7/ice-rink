package by.tami.skateservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GetSkatesArgs {
    private Integer size;
    private String cursor;
}
