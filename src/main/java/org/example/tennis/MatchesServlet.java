package org.example.tennis;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.util.List;

@WebServlet("/matches")
public class MatchesServlet extends HttpServlet {
    private static final int MATCHES_PER_PAGE = 5;

    int currentPage;
    int totalPages;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionFactory sessionFactory = SessionFactoryManager.getInstance().getSessionFactory();
        MatchService matchService = new MatchService(sessionFactory);
        PlayerService playerService = new PlayerService(sessionFactory);
        PageResolver pageResolver = new PageResolver();

        String page = request.getParameter("page");
        String playerNameFilter = request.getParameter("filter_by_player_name");


        currentPage = pageResolver.resolvePage(page);
        int paginationStartIndex = (currentPage - 1) * MATCHES_PER_PAGE;


        if (playerNameFilter == null) {          //то фильтровать не надо  (PlayerNameValidator будет мб)
            List<MatchEntity> matches = matchService.getPage(paginationStartIndex);
            long totalMatches = matchService.count();          //сделаешь обработку ошибок если вернулся пустой список этот случай нужно предусмотреть а то NPE будет

            totalPages = countTotalPages(totalMatches);

            if (matches.size() == 0) {
                populateEmptyMatchAttributes(request);
                request.getRequestDispatcher("/WEB-INF/matches.jsp").forward(request, response);
                return;
            }

            populateMatchAttributes(request, matches);
            request.getRequestDispatcher("/WEB-INF/matches.jsp").forward(request, response);
            return;
        }

        if (playerNameFilter != null) {     //то фильтровать надо

            PlayerEntity player = playerService.getByName(playerNameFilter);
            List<MatchEntity> matches = matchService.getPageWithPlayerFilter(player, paginationStartIndex);
            long totalMatches = matchService.countWithPlayerFilter(player);

            totalPages = countTotalPages(totalMatches);

            if (matches.size() == 0) {
                populateEmptyMatchAttributes(request);
                request.getRequestDispatcher("/WEB-INF/matches.jsp").forward(request, response);
                return;
            }

            populateMatchAttributes(request, matches);
            request.getRequestDispatcher("/WEB-INF/matches.jsp").forward(request, response);
        }
        }





        private int countTotalPages ( long matches){
            return (int) Math.ceil((double) matches / MATCHES_PER_PAGE);
        }

        private void populateMatchAttributes (HttpServletRequest request, List < MatchEntity > matches){
            for (int i = 0; i < matches.size(); i++) {
                MatchEntity match = matches.get(i);
                PlayerEntity firstPlayer = match.getPlayer1();
                PlayerEntity secondPlayer = match.getPlayer2();
                PlayerEntity winner = match.getWinner();

                String firstPlayerFormName = "firstPlayerName" + (i + 1);
                String secondPlayerFormName = "secondPlayerName" + (i + 1);
                String winnerFormName = "winnerName" + (i + 1);

                request.setAttribute(firstPlayerFormName, firstPlayer.getName());
                request.setAttribute(secondPlayerFormName, secondPlayer.getName());
                request.setAttribute(winnerFormName, winner.getName());

                request.setAttribute("currentPage", currentPage);
                request.setAttribute("totalPages", totalPages);
            }
        }

        private void populateEmptyMatchAttributes (HttpServletRequest request){
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
        }
    }












