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
    private final MatchPageViewService paginatedMatchViewService = new MatchPageViewService(sessionFactory);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = request.getParameter("page");
        String playerNameFilter = request.getParameter("filter_by_player_name");

        MatchPageViewDto matchPage = paginatedMatchViewService.getPage(page, playerNameFilter);

        request.setAttribute("matchPage", matchPage);
        request.getRequestDispatcher("/WEB-INF/matches.jsp").forward(request, response);
    }
}












