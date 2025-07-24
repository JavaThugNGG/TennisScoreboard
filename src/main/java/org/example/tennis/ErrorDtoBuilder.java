package org.example.tennis;

public class ErrorDtoBuilder {
    private final StatusCodeProcessor statusCodeProcessor = new StatusCodeProcessor();

    public ErrorDto build(Throwable throwable) {
        int statusCode = statusCodeProcessor.resolveStatusCode(throwable);
        String message = "Ошибка: " + throwable.getMessage();
        return new ErrorDto(statusCode, message);
    }
}
