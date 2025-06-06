package org.example.tennis;

public class MatchScore {
    String firstPlayerName;
    String secondPlayerName;

    int firstPlayerSets;
    int secondPlayerSets;

    int firstPlayerGames;
    int secondPlayerGames;

    int firstPlayerPoints;
    int secondPlayerPoints;


    public MatchScore(String firstPlayerName, String secondPlayerName, int firstPlayerSets, int secondPlayerSets, int firstPlayerGames, int secondPlayerGames, int firstPlayerPoints, int secondPlayerPoints) {
        this.firstPlayerName = firstPlayerName;
        this.secondPlayerName = secondPlayerName;
        this.firstPlayerSets = firstPlayerSets;
        this.secondPlayerSets = secondPlayerSets;
        this.firstPlayerGames = firstPlayerGames;
        this.secondPlayerGames = secondPlayerGames;
        this.firstPlayerPoints = firstPlayerPoints;
        this.secondPlayerPoints = secondPlayerPoints;
    }

    public String getFirstPlayerName() {
        return firstPlayerName;
    }

    public void setFirstPlayerName(String firstPlayerName) {
        this.firstPlayerName = firstPlayerName;
    }

    public String getSecondPlayerName() {
        return secondPlayerName;
    }

    public void setSecondPlayerName(String secondPlayerName) {
        this.secondPlayerName = secondPlayerName;
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
}

