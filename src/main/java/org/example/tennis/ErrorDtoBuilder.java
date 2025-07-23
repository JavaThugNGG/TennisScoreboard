package org.example.tennis;

import jakarta.servlet.http.HttpServletRequest;

public class ErrorDtoBuilder {
    private final StatusCodeMapper statusCodeMapper = new StatusCodeMapper();
    private final PathProcessor pathProcessor = new PathProcessor();

    public ErrorDto build(HttpServletRequest request, Throwable throwable) {
        String uri = request.getRequestURI();
        String forwardPath = pathProcessor.resolveForwardPath(uri);
        int statusCode = statusCodeMapper.getStatusCode(throwable);
        String message = "Ошибка: " + throwable.getMessage();
        return new ErrorDto(statusCode, message, forwardPath);
    }
}
