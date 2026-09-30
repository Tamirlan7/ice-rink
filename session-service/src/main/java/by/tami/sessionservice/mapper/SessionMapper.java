package by.tami.sessionservice.mapper;

import by.tami.sessionservice.dto.SessionDto;
import by.tami.sessionservice.model.Session;

public class SessionMapper {
    public static SessionDto toDto(Session session) {
        return new SessionDto(
                session.getId(),
                session.getDate(),
                session.getStartTime(),
                session.getEndTime(),
                session.getAllowedPeopleQuantity(),
                session.getCurrentlyPeopleQuantity(),
                session.getStatus()
        );
    }
}
