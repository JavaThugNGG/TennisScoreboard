package org.example.tennis;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class ExceptionFilter extends HttpFilter {
    private final ErrorDtoBuilder errorDtoBuilder = new ErrorDtoBuilder();

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        String path = request.getRequestURI();

        if (path.startsWith("/api/css/") || path.startsWith("/api/js/") || path.startsWith("/api/images/")) {
            chain.doFilter(request, resp);
            return;
        }

        try {
            chain.doFilter(request, resp);
        } catch (Throwable throwable) {
            ErrorDto error = errorDtoBuilder.build(request, throwable);
            resp.setStatus(error.getStatusCode());
            request.setAttribute("errorMessage", error.getMessage());
            request.getRequestDispatcher(error.getForwardPath()).forward(request, resp);

        }
    }
}
