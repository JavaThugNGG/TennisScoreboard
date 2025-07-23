package org.example.tennis;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {
    private final SessionFactory sessionFactory = SessionFactoryManager.getInstance().getSessionFactory();
    private final PlayerService playerService = new PlayerService(sessionFactory);
    private final MatchInitializationService matchInitializationService = new MatchInitializationService(sessionFactory, playerService);
    private final OngoingMatchesService ongoingMatchesService = OngoingMatchesService.getInstance();
    private final PlayerValidator playerValidator = new PlayerValidator();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/new-match.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String firstPlayerName = request.getParameter("playerOne");
        String secondPlayerName = request.getParameter("playerTwo");

        playerValidator.validateName(firstPlayerName);
        playerValidator.validateName(secondPlayerName);

        MatchScoreModel matchScoreModel = matchInitializationService.persistPlayers(firstPlayerName, secondPlayerName);
        UUID pastedMatchId = ongoingMatchesService.addMatch(matchScoreModel);

        String contextPath = request.getContextPath();
        response.sendRedirect(contextPath + "/match-score?uuid=" + pastedMatchId);
    }
}
