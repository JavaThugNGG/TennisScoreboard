package org.example.tennis;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchAttributePopulator {

    public Map<String, String> populate(HttpServletRequest request, List<MatchEntity> matches, int currentPage, int totalPages) {
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);

        Map<String, String> populatedPage = new HashMap<>();

        if (matches == null || matches.size() == 0) {
            return populatedPage;
        }

        for (int i = 0; i < matches.size(); i++) {
            MatchEntity match = matches.get(i);
            String firstPlayerName = match.getPlayer1().getName();
            String secondPlayerName = match.getPlayer2().getName();
            String winnerName = match.getWinner().getName();

            String firstPlayerFormName = "firstPlayerName" + (i + 1);
            String secondPlayerFormName = "secondPlayerName" + (i + 1);
            String winnerFormName = "winnerName" + (i + 1);

            populatedPage.put(firstPlayerFormName, firstPlayerName);
            populatedPage.put(secondPlayerFormName, secondPlayerName);
            populatedPage.put(winnerFormName, winnerName);
        }

        return populatedPage;
    }
}
