package org.example.tennis;

import jakarta.servlet.http.HttpServletRequest;

public class ErrorDtoBuilder {
    private final StatusCodeProcessor statusCodeProcessor = new StatusCodeProcessor();
    private final PathProcessor pathProcessor = new PathProcessor();

    public ErrorDto build(HttpServletRequest request, Throwable throwable) {
        String uri = request.getRequestURI();
        int statusCode = statusCodeProcessor.resolveStatusCode(throwable);
        String message = "Ошибка: " + throwable.getMessage();
        return new ErrorDto(statusCode, message);
    }
}
