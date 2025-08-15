package org.example.tennis.dto.builder;

import org.example.tennis.entity.MatchEntity;
import org.example.tennis.dto.MatchPageViewDto;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchPageViewDtoBuilder {

    public MatchPageViewDto build(List<MatchEntity> matches, int currentPage, int totalPages) {
        MatchPageViewDto dto = new MatchPageViewDto();
        if (matches.isEmpty()) {
            dto.setMatchAttributes(Collections.emptyMap());
        } else {
            Map<String, String> attributes = getAttributes(matches);
            dto.setMatchAttributes(attributes);
        }
        dto.setCurrentPage(currentPage);
        dto.setTotalPages(totalPages);
        return dto;
    }

    private Map<String, String> getAttributes(List<MatchEntity> matches) {
        Map<String, String> attributes = new HashMap<>();
        for (int i = 0; i < matches.size(); i++) {
            MatchEntity match = matches.get(i);
            String firstPlayerName = match.getPlayer1().getName();
            String secondPlayerName = match.getPlayer2().getName();
            String winnerName = match.getWinner().getName();

            String firstPlayerFormName = "firstPlayerName" + (i + 1);
            String secondPlayerFormName = "secondPlayerName" + (i + 1);
            String winnerFormName = "winnerName" + (i + 1);

            attributes.put(firstPlayerFormName, firstPlayerName);
            attributes.put(secondPlayerFormName, secondPlayerName);
            attributes.put(winnerFormName, winnerName);
        }
        return attributes;
    }
}
