package org.example.tennis;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@WebServlet("/matches")
public class MatchesServlet extends HttpServlet {
    private SessionFactory sessionFactory;

    int pageInt;

    int totalPages;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        sessionFactory = (SessionFactory) getServletContext().getAttribute("SessionFactory");

        String page = request.getParameter("page");

        if (page != null) {
            try {
                pageInt = Integer.parseInt(page);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else {
            pageInt = 1;
        }



        //проверяешь параметры
        //если null - отображаешь последние 5
        //по дефолту сейчас сделай отображение последних 5 !!!




        //нужно отобразить jsp со всеми подтянутыми матчами

        //запрос который вытянет последние 5 игроков

        int fromIndex = (pageInt - 1) * 5;

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        List<MatchEntity> matches = session.createQuery("FROM MatchEntity m ORDER BY m.id DESC", MatchEntity.class)
                .setFirstResult(fromIndex) // начиная с 6-й записи (индекс 5)
                .setMaxResults(5)           // получить 5 записей
                .getResultList();

        System.out.println(matches.size());


        Long totalMatches = (Long) session.createQuery("SELECT COUNT(m) FROM MatchEntity m").uniqueResult();
        totalPages = (int) Math.ceil((double) totalMatches / 5);


        session.getTransaction().commit();


        if (matches.size() == 0) {
            request.getRequestDispatcher("/WEB-INF/matches.jsp").forward(request, response);
            request.setAttribute("currentPage", pageInt);
            request.setAttribute("totalPages", totalPages);
            return;
        }

        if (matches.size() % 5 == 1) {
            MatchEntity match = matches.get(0);


            System.out.println(match.toString());



            PlayerEntity firstPlayer = match.getPlayer1();
            PlayerEntity secondPlayer= match.getPlayer1();
            PlayerEntity winner = match.getPlayer1();

            String firstPlayerName = firstPlayer.getName();
            String secondPlayerName = secondPlayer.getName();
            String winnerName = winner.getName();

            request.setAttribute("firstPlayerName1", firstPlayerName);
            request.setAttribute("secondPlayerName1", secondPlayerName);
            request.setAttribute("winnerName1", winnerName);

            request.setAttribute("currentPage", pageInt);
            request.setAttribute("totalPages", totalPages);


            request.getRequestDispatcher("/WEB-INF/matches.jsp").forward(request, response);
            return;
        }

        if (matches.size() % 5  == 2) {
            MatchEntity match1 = matches.get(0);
            PlayerEntity firstPlayer1 = match1.getPlayer1();
            PlayerEntity secondPlayer1= match1.getPlayer2();
            PlayerEntity winner1 = match1.getWinner();

            String firstPlayerName1 = firstPlayer1.getName();
            String secondPlayerName1 = secondPlayer1.getName();
            String winnerName1 = winner1.getName();

            request.setAttribute("firstPlayerName1", firstPlayerName1);
            request.setAttribute("secondPlayerName1", secondPlayerName1);
            request.setAttribute("winnerName1", winnerName1);


            MatchEntity match2 = matches.get(1);
            PlayerEntity firstPlayer2 = match2.getPlayer1();
            PlayerEntity secondPlayer2= match2.getPlayer2();
            PlayerEntity winner2 = match2.getWinner();

            String firstPlayerName2 = firstPlayer2.getName();
            String secondPlayerName2 = secondPlayer2.getName();
            String winnerName2 = winner2.getName();

            request.setAttribute("firstPlayerName2", firstPlayerName2);
            request.setAttribute("secondPlayerName2", secondPlayerName2);
            request.setAttribute("winnerName2", winnerName2);

            request.setAttribute("currentPage", pageInt);
            request.setAttribute("totalPages", totalPages);

            request.getRequestDispatcher("/WEB-INF/matches.jsp").forward(request, response);
            return;
        }

        if (matches.size() % 5 == 3) {
            MatchEntity match1 = matches.get(0);
            PlayerEntity firstPlayer1 = match1.getPlayer1();
            PlayerEntity secondPlayer1= match1.getPlayer2();
            PlayerEntity winner1 = match1.getWinner();

            String firstPlayerName1 = firstPlayer1.getName();
            String secondPlayerName1 = secondPlayer1.getName();
            String winnerName1 = winner1.getName();

            request.setAttribute("firstPlayerName1", firstPlayerName1);
            request.setAttribute("secondPlayerName1", secondPlayerName1);
            request.setAttribute("winnerName1", winnerName1);


            MatchEntity match2 = matches.get(1);
            PlayerEntity firstPlayer2 = match2.getPlayer1();
            PlayerEntity secondPlayer2= match2.getPlayer2();
            PlayerEntity winner2 = match2.getWinner();

            String firstPlayerName2 = firstPlayer2.getName();
            String secondPlayerName2 = secondPlayer2.getName();
            String winnerName2 = winner2.getName();

            request.setAttribute("firstPlayerName2", firstPlayerName2);
            request.setAttribute("secondPlayerName2", secondPlayerName2);
            request.setAttribute("winnerName2", winnerName2);


            MatchEntity match3 = matches.get(2);
            PlayerEntity firstPlayer3 = match3.getPlayer1();
            PlayerEntity secondPlayer3= match3.getPlayer2();
            PlayerEntity winner3 = match3.getWinner();

            String firstPlayerName3 = firstPlayer3.getName();
            String secondPlayerName3 = secondPlayer3.getName();
            String winnerName3 = winner3.getName();

            request.setAttribute("firstPlayerName3", firstPlayerName3);
            request.setAttribute("secondPlayerName3", secondPlayerName3);
            request.setAttribute("winnerName3", winnerName3);

            request.setAttribute("currentPage", pageInt);
            request.setAttribute("totalPages", totalPages);

            request.getRequestDispatcher("/WEB-INF/matches.jsp").forward(request, response);
            return;
        }


        if (matches.size() % 5 == 4) {
            MatchEntity match1 = matches.get(0);
            PlayerEntity firstPlayer1 = match1.getPlayer1();
            PlayerEntity secondPlayer1= match1.getPlayer2();
            PlayerEntity winner1 = match1.getWinner();

            String firstPlayerName1 = firstPlayer1.getName();
            String secondPlayerName1 = secondPlayer1.getName();
            String winnerName1 = winner1.getName();

            request.setAttribute("firstPlayerName1", firstPlayerName1);
            request.setAttribute("secondPlayerName1", secondPlayerName1);
            request.setAttribute("winnerName1", winnerName1);


            MatchEntity match2 = matches.get(1);
            PlayerEntity firstPlayer2 = match2.getPlayer1();
            PlayerEntity secondPlayer2= match2.getPlayer2();
            PlayerEntity winner2 = match2.getWinner();

            String firstPlayerName2 = firstPlayer2.getName();
            String secondPlayerName2 = secondPlayer2.getName();
            String winnerName2 = winner2.getName();

            request.setAttribute("firstPlayerName2", firstPlayerName2);
            request.setAttribute("secondPlayerName2", secondPlayerName2);
            request.setAttribute("winnerName2", winnerName2);


            MatchEntity match3 = matches.get(2);
            PlayerEntity firstPlayer3 = match3.getPlayer1();
            PlayerEntity secondPlayer3= match3.getPlayer2();
            PlayerEntity winner3 = match3.getWinner();

            String firstPlayerName3 = firstPlayer3.getName();
            String secondPlayerName3 = secondPlayer3.getName();
            String winnerName3 = winner3.getName();

            request.setAttribute("firstPlayerName3", firstPlayerName3);
            request.setAttribute("secondPlayerName3", secondPlayerName3);
            request.setAttribute("winnerName3", winnerName3);


            MatchEntity match4 = matches.get(3);
            PlayerEntity firstPlayer4 = match4.getPlayer1();
            PlayerEntity secondPlayer4= match4.getPlayer2();
            PlayerEntity winner4 = match4.getWinner();

            String firstPlayerName4 = firstPlayer4.getName();
            String secondPlayerName4 = secondPlayer4.getName();
            String winnerName4 = winner4.getName();

            request.setAttribute("firstPlayerName4", firstPlayerName4);
            request.setAttribute("secondPlayerName4", secondPlayerName4);
            request.setAttribute("winnerName4", winnerName4);

            request.setAttribute("currentPage", pageInt);
            request.setAttribute("totalPages", totalPages);

            request.getRequestDispatcher("/WEB-INF/matches.jsp").forward(request, response);
            return;
        }


        if (matches.size() % 5 == 0) {
            MatchEntity match1 = matches.get(0);
            PlayerEntity firstPlayer1 = match1.getPlayer1();
            PlayerEntity secondPlayer1= match1.getPlayer2();
            PlayerEntity winner1 = match1.getWinner();

            String firstPlayerName1 = firstPlayer1.getName();
            String secondPlayerName1 = secondPlayer1.getName();
            String winnerName1 = winner1.getName();

            request.setAttribute("firstPlayerName1", firstPlayerName1);
            request.setAttribute("secondPlayerName1", secondPlayerName1);
            request.setAttribute("winnerName1", winnerName1);


            MatchEntity match2 = matches.get(1);
            PlayerEntity firstPlayer2 = match2.getPlayer1();
            PlayerEntity secondPlayer2= match2.getPlayer2();
            PlayerEntity winner2 = match2.getWinner();

            String firstPlayerName2 = firstPlayer2.getName();
            String secondPlayerName2 = secondPlayer2.getName();
            String winnerName2 = winner2.getName();

            request.setAttribute("firstPlayerName2", firstPlayerName2);
            request.setAttribute("secondPlayerName2", secondPlayerName2);
            request.setAttribute("winnerName2", winnerName2);


            MatchEntity match3 = matches.get(2);
            PlayerEntity firstPlayer3 = match3.getPlayer1();
            PlayerEntity secondPlayer3= match3.getPlayer2();
            PlayerEntity winner3 = match3.getWinner();

            String firstPlayerName3 = firstPlayer3.getName();
            String secondPlayerName3 = secondPlayer3.getName();
            String winnerName3 = winner3.getName();

            request.setAttribute("firstPlayerName3", firstPlayerName3);
            request.setAttribute("secondPlayerName3", secondPlayerName3);
            request.setAttribute("winnerName3", winnerName3);


            MatchEntity match4 = matches.get(3);
            PlayerEntity firstPlayer4 = match4.getPlayer1();
            PlayerEntity secondPlayer4= match4.getPlayer2();
            PlayerEntity winner4 = match4.getWinner();

            String firstPlayerName4 = firstPlayer4.getName();
            String secondPlayerName4 = secondPlayer4.getName();
            String winnerName4 = winner4.getName();

            request.setAttribute("firstPlayerName4", firstPlayerName4);
            request.setAttribute("secondPlayerName4", secondPlayerName4);
            request.setAttribute("winnerName4", winnerName4);


            MatchEntity match5 = matches.get(4);
            PlayerEntity firstPlayer5 = match5.getPlayer1();
            PlayerEntity secondPlayer5= match5.getPlayer2();
            PlayerEntity winner5 = match5.getWinner();

            String firstPlayerName5 = firstPlayer5.getName();
            String secondPlayerName5 = secondPlayer5.getName();
            String winnerName5 = winner5.getName();

            request.setAttribute("firstPlayerName5", firstPlayerName5);
            request.setAttribute("secondPlayerName5", secondPlayerName5);
            request.setAttribute("winnerName5", winnerName5);

            request.setAttribute("currentPage", pageInt);
            request.setAttribute("totalPages", totalPages);

            request.getRequestDispatcher("/WEB-INF/matches.jsp").forward(request, response);
            return;
        }

        //а теперь если страница передана

    }












}
