package by.tami.sessionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetSessionsResponse {
    private List<SessionDto> sessions;
}
