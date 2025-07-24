package org.example.tennis;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;

import java.io.IOException;

@WebServlet("/matches")
public class MatchesServlet extends HttpServlet {
    private final SessionFactory sessionFactory = SessionFactoryManager.getInstance().getSessionFactory();
    private final MatchDao matchDao = new MatchDao();
    private final PlayerDao playerDao = new PlayerDao();
    private final PlayerValidator playerValidator = new PlayerValidator();
    private final PlayerService playerService = new PlayerService(sessionFactory, playerDao);
    private final MatchService matchService = new MatchService(sessionFactory, matchDao);
    private final MatchesSummaryService matchesSummaryService = new MatchesSummaryService(matchService, playerService, playerValidator);

    private final PageProcessor pageProcessor = new PageProcessor();
    private final MatchPageViewDtoBuilder matchPageViewDtoBuilder = new MatchPageViewDtoBuilder();
    private final MatchPageViewService matchPageViewService = new MatchPageViewService(pageProcessor, matchesSummaryService, matchPageViewDtoBuilder, playerValidator);

    private final ErrorDtoBuilder errorDtoBuilder = new ErrorDtoBuilder();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = request.getParameter("page");
        String playerNameFilter = request.getParameter("filter_by_player_name");

        MatchPageViewDto matchPage;

        try {
            matchPage = matchPageViewService.getPage(page, playerNameFilter);
        } catch (IllegalPlayerNameFilterException e) {
            matchPage = matchPageViewService.getPage(page, null);
            ErrorDto error = errorDtoBuilder.build(e);
            response.setStatus(error.getStatusCode());
            request.setAttribute("errorMessage", error.getMessage());
        }

        request.setAttribute("matchPage", matchPage);
        request.getRequestDispatcher("/WEB-INF/matches.jsp").forward(request, response);
    }
}




