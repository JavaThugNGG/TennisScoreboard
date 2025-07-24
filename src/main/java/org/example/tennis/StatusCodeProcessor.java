package org.example.tennis;

import jakarta.servlet.http.HttpServletResponse;

public class StatusCodeProcessor {

    public int resolveStatusCode(Throwable throwable) {
        return switch (throwable.getClass().getSimpleName()) {
            case "IllegalArgumentException" -> HttpServletResponse.SC_BAD_REQUEST;
            default -> HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        };
    }
}
