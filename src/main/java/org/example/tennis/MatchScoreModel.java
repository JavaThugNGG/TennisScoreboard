package org.example.tennis;

import java.util.UUID;

public class MatchScoreModel {
    private int firstPlayerId;
    private int secondPlayerId;

    private int firstPlayerSets;
    private int secondPlayerSets;

    private int firstPlayerGames;
    private int secondPlayerGames;

    private int firstPlayerPoints;
    private int secondPlayerPoints;

    private int firstPlayerAdvantage = 0;
    private int secondPlayerAdvantage = 0;


    public MatchScoreModel(int firstPlayerId, int secondPlayerId, int firstPlayerSets, int secondPlayerSets, int firstPlayerGames, int secondPlayerGames, int firstPlayerPoints, int secondPlayerPoints) {
        this.firstPlayerId = firstPlayerId;
        this.secondPlayerId = secondPlayerId;
        this.firstPlayerSets = firstPlayerSets;
        this.secondPlayerSets = secondPlayerSets;
        this.firstPlayerGames = firstPlayerGames;
        this.secondPlayerGames = secondPlayerGames;
        this.firstPlayerPoints = firstPlayerPoints;
        this.secondPlayerPoints = secondPlayerPoints;
    }

    public int getFirstPlayerId() {
        return firstPlayerId;
    }

    public void setFirstPlayerId(int firstPlayerId) {
        this.firstPlayerId = firstPlayerId;
    }

    public int getSecondPlayerId() {
        return secondPlayerId;
    }

    public void setSecondPlayerId(int secondPlayerId) {
        this.secondPlayerId = secondPlayerId;
    }

    public int getFirstPlayerSets() {
        return firstPlayerSets;
    }

    public void setFirstPlayerSets(int firstPlayerSets) {
        this.firstPlayerSets = firstPlayerSets;
    }

    public int getSecondPlayerSets() {
        return secondPlayerSets;
    }

    public void setSecondPlayerSets(int secondPlayerSets) {
        this.secondPlayerSets = secondPlayerSets;
    }

    public int getFirstPlayerGames() {
        return firstPlayerGames;
    }

    public void setFirstPlayerGames(int firstPlayerGames) {
        this.firstPlayerGames = firstPlayerGames;
    }

    public int getSecondPlayerGames() {
        return secondPlayerGames;
    }

    public void setSecondPlayerGames(int secondPlayerGames) {
        this.secondPlayerGames = secondPlayerGames;
    }

    public int getFirstPlayerPoints() {
        return firstPlayerPoints;
    }

    public void setFirstPlayerPoints(int firstPlayerPoints) {
        this.firstPlayerPoints = firstPlayerPoints;
    }

    public int getSecondPlayerPoints() {
        return secondPlayerPoints;
    }

    public void setSecondPlayerPoints(int secondPlayerPoints) {
        this.secondPlayerPoints = secondPlayerPoints;
    }

    public int getFirstPlayerAdvantage() {
        return firstPlayerAdvantage;
    }

    public void setFirstPlayerAdvantage(int firstPlayerAdvantage) {
        this.firstPlayerAdvantage = firstPlayerAdvantage;
    }

    public int getSecondPlayerAdvantage() {
        return secondPlayerAdvantage;
    }

    public void setSecondPlayerAdvantage(int secondPlayerAdvantage) {
        this.secondPlayerAdvantage = secondPlayerAdvantage;
    }
}

