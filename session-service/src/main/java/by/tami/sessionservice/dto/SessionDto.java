package by.tami.sessionservice.dto;

import by.tami.sessionservice.model.SessionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class SessionDto {
    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private short allowedPeopleQuantity;
    private short currentlyPeopleQuantity;
    private SessionStatus status;

    public short getAvailableSpots() {
        return (short) (allowedPeopleQuantity - currentlyPeopleQuantity);
    }
}
