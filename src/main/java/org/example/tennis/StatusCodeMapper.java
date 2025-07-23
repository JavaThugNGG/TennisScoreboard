package org.example.tennis;

import jakarta.servlet.http.HttpServletResponse;

public class StatusCodeMapper {

    public int getStatusCode(Throwable throwable) {
        return switch (throwable.getClass().getSimpleName()) {
            case "IllegalArgumentException" -> HttpServletResponse.SC_BAD_REQUEST;
            case "ElementNotFoundException" -> HttpServletResponse.SC_NOT_FOUND;
            case "ElementAlreadyExistsException" -> HttpServletResponse.SC_CONFLICT;
            case "DatabaseException" -> HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            default -> HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        };
    }
}
