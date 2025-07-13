package org.example.tennis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchPageMapper {

    public MatchPageDto convert(List<MatchEntity> matches, int currentPage, int totalPages) {
        MatchPageDto matchPageDto = new MatchPageDto();
        Map<String, String> matchesAttributes = new HashMap<>();

        if (!matches.isEmpty()) {
            fillAttributes(matches, matchesAttributes);
        }

        matchPageDto.setMatchAttributes(matchesAttributes);
        matchPageDto.setCurrentPage(currentPage);
        matchPageDto.setTotalPages(totalPages);
        return matchPageDto;
    }

    private void fillAttributes(List<MatchEntity> matches, Map<String, String> matchesAttributes) {
        for (int i = 0; i < matches.size(); i++) {
            MatchEntity match = matches.get(i);
            String firstPlayerName = match.getPlayer1().getName();
            String secondPlayerName = match.getPlayer2().getName();
            String winnerName = match.getWinner().getName();

            String firstPlayerFormName = "firstPlayerName" + (i + 1);
            String secondPlayerFormName = "secondPlayerName" + (i + 1);
            String winnerFormName = "winnerName" + (i + 1);

            matchesAttributes.put(firstPlayerFormName, firstPlayerName);
            matchesAttributes.put(secondPlayerFormName, secondPlayerName);
            matchesAttributes.put(winnerFormName, winnerName);
        }
    }
}
