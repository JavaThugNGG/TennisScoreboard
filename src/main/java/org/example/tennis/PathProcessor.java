package org.example.tennis;

public class PathProcessor {

    public String resolveForwardPath(String uri) {
        return switch (uri) {
            case "/tennis_war_exploded/new-match" -> "/WEB-INF/new-match.jsp";
            case "/tennis_war_exploded/matches" -> "/WEB-INF/matches.jsp";
            default -> throw new RuntimeException("Ошибка: Отсутствует блок обработки для данного пути");
        };
    }
}
