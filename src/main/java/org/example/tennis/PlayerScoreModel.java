package org.example.tennis;

public class PlayerScoreModel {
    private final MatchScoreModel match;
    private final PlayerSide player;

    public PlayerScoreModel(MatchScoreModel match, PlayerSide player) {
        this.match = match;
        this.player = player;
    }

    public int getPoints() {
        if (player == PlayerSide.FIRST) {
            return match.getFirstPlayerPoints();
        } else {
            return match.getSecondPlayerPoints();
        }
    }

    public void setPoints(int points) {
        if (player == PlayerSide.FIRST) {
            match.setFirstPlayerPoints(points);
        } else {
            match.setSecondPlayerPoints(points);
        }
    }

    public int getGames() {
        if (player == PlayerSide.FIRST) {
            return match.getFirstPlayerGames();
        } else {
            return match.getSecondPlayerGames();
        }
    }

    public void setGames(int games) {
        if (player == PlayerSide.FIRST) {
            match.setFirstPlayerGames(games);
        } else {
            match.setSecondPlayerGames(games);
        }
    }

    public int getSets() {
        if (player == PlayerSide.FIRST) {
            return match.getFirstPlayerSets();
        } else {
            return match.getSecondPlayerSets();
        }
    }

    public void setSets(int sets) {
        if (player == PlayerSide.FIRST) {
            match.setFirstPlayerSets(sets);
        } else {
            match.setSecondPlayerSets(sets);
        }
    }

    public boolean isAdvantage() {
        if (player == PlayerSide.FIRST) {
            return match.isFirstPlayerAdvantage();
        } else {
            return match.isSecondPlayerAdvantage();
        }
    }

    public void setAdvantage(boolean advantage) {
        if (player == PlayerSide.FIRST) {
            match.setFirstPlayerAdvantage(advantage);
        } else {
            match.setSecondPlayerAdvantage(advantage);
        }
    }
}

