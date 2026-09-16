package by.tami.skateservice.util;

import by.tami.skateservice.exception.BadRequestException;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CursorEncoder {
    public static String encode(Long id) {
        if (id == null) return null;

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(String.valueOf(id).getBytes(StandardCharsets.UTF_8));
    }

    public static Long decode(String cursor) {
        if (cursor == null || cursor.isBlank()) return null;

        try {
            byte[] decodedBytes = Base64.getUrlDecoder().decode(cursor);
            return Long.parseLong(new String(decodedBytes, StandardCharsets.UTF_8));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Неверный формат курсора");
        }
    }
}
