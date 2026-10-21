package by.tami.sessionservice.mapper;

import by.tami.grpc.session.GetSessionByIdResponse;
import by.tami.grpc.session.SessionStatusProto;
import by.tami.sessionservice.model.Session;
import by.tami.sessionservice.model.SessionStatus;
import com.google.type.Date;
import com.google.type.TimeOfDay;

import java.time.LocalDate;
import java.time.LocalTime;

public class SessionProtoMapper {

    public static GetSessionByIdResponse toProto(Session s) {
        return GetSessionByIdResponse.newBuilder()
                .setId(s.getId())
                .setDate(toProto(s.getDate()))
                .setStartTime(toProto(s.getStartTime()))
                .setEndTime(toProto(s.getEndTime()))
                .setAllowedPeopleQuantity(s.getAllowedPeopleQuantity())
                .setCurrentlyPeopleQuantity(s.getCurrentlyPeopleQuantity())
                .setStatus(toProto(s.getStatus()))
                .build();
    }

    private static Date toProto(LocalDate date) {
        return Date.newBuilder()
                .setYear(date.getYear())
                .setMonth(date.getMonthValue())
                .setDay(date.getDayOfMonth())
                .build();
    }

    private static TimeOfDay toProto(LocalTime time) {
        return TimeOfDay.newBuilder()
                .setHours(time.getHour())
                .setMinutes(time.getMinute())
                .setSeconds(time.getSecond())
                .build();
    }

    private static SessionStatusProto toProto(SessionStatus status) {
        return switch (status) {
            case SCHEDULED -> SessionStatusProto.SESSION_STATUS_SCHEDULED;
            case COMPLETED -> SessionStatusProto.SESSION_STATUS_COMPLETED;
            case CANCELLED -> SessionStatusProto.SESSION_STATUS_CANCELLED;
            default -> SessionStatusProto.SESSION_STATUS_UNSPECIFIED;
        };
    }
}
