package org.example.tennis;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.util.Map;

@WebServlet("/matches")
public class MatchesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionFactory sessionFactory = SessionFactoryManager.getInstance().getSessionFactory();    //отдать хендлеру надо будет
        MatchPageHandler matchPageHandler = new MatchPageHandler();

        String page = request.getParameter("page");
        String playerNameFilter = request.getParameter("filter_by_player_name");

        Map<String, String> map = matchPageHandler.handleRequest(request, sessionFactory, page, playerNameFilter);
        request.setAttribute("matches", map);   //ключ значение положили значение для передачи
        request.getRequestDispatcher("/WEB-INF/matches.jsp").forward(request, response);
    }
}












