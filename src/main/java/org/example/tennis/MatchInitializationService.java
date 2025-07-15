package org.example.tennis;

import org.hibernate.SessionFactory;

public class MatchInitializationService {
    private final PlayerService playerService;

    MatchInitializationService(SessionFactory sessionFactory, PlayerService playerService) {
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
