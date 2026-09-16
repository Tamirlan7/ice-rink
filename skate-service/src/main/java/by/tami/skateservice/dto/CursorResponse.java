package by.tami.skateservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class CursorResponse<T> {
    private List<T> content;
    private Integer size;
    private String nextCursor;
    private Boolean hasNext;
}
