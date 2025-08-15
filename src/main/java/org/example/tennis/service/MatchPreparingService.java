package org.example.tennis.service;

import org.example.tennis.model.MatchScoreModel;
import org.example.tennis.exception.PlayerAlreadyExistsException;

public class MatchPreparingService {
    private final PlayerService playerService;

    public MatchPreparingService(PlayerService playerService) {
        this.playerService = playerService;
    }

    public MatchScoreModel persistPlayers(String firstPlayerName, String secondPlayerName) {
        int firstPlayerId = insertPlayer(firstPlayerName);
        int secondPlayerId = insertPlayer(secondPlayerName);
        return new MatchScoreModel(firstPlayerId, secondPlayerId);
    }

    private int insertPlayer(String playerName) {
        try {
            return playerService.insert(playerName).getId();
        } catch (PlayerAlreadyExistsException e) {
            return playerService.getByName(playerName).getId();
        }
    }
}
