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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionFactory sessionFactory = SessionFactoryManager.getInstance().getSessionFactory();
        MatchPageService matchPageService = new MatchPageService(sessionFactory);

        String page = request.getParameter("page");
        String playerNameFilter = request.getParameter("filter_by_player_name");    //тут будет валидация

        MatchPageDto matchpageDto = matchPageService.fetch(page, playerNameFilter);

        request.setAttribute("matchPage", matchpageDto);
        request.getRequestDispatcher("/WEB-INF/matches.jsp").forward(request, response);
    }
}












