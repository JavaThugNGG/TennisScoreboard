package org.example.tennis;

public class MatchScoreCalculationService {
    MatchStateValidator matchStateValidator = new MatchStateValidator();

    public void scoringFirstPlayer(MatchScoreModel matchScoreModel) {   //для тестов передается matchScore и смотрим что например не обновился как и надо при 40:40
        scoringFirstPlayerPoints(matchScoreModel);
        matchStateValidator.validateMatchNotFinished(matchScoreModel);
    }

    public void scoringSecondPlayer(MatchScoreModel matchScoreModel) {
        scoringSecondPlayerPoints(matchScoreModel);
        matchStateValidator.validateMatchNotFinished(matchScoreModel);
    }

    private void scoringFirstPlayerPoints(MatchScoreModel matchScoreModel) {

        int currentPoints = matchScoreModel.getFirstPlayerPoints();

        if (currentPoints == 0) {
            int newPoints = 15;
            matchScoreModel.setFirstPlayerPoints(newPoints);
        }

        if (currentPoints == 15) {
            int newPoints = 30;
            matchScoreModel.setFirstPlayerPoints(newPoints);
        }

        if (currentPoints == 30) {
            int newPoints = 40;
            matchScoreModel.setFirstPlayerPoints(newPoints);
        }

        if (currentPoints == 40) {
            int secondPlayerPoints = matchScoreModel.getSecondPlayerPoints();

            if (secondPlayerPoints < 40) {
                updateFirstPlayerGames(matchScoreModel);
            }

            if (secondPlayerPoints == 40) {
                if (matchScoreModel.getFirstPlayerAdvantage() - matchScoreModel.getSecondPlayerAdvantage() == 2) {
                    updateFirstPlayerGames(matchScoreModel);
                } else {
                    int firstPlayerCurrentAdvantage = matchScoreModel.getFirstPlayerAdvantage();
                    int newFirstPlayerAdvantage = firstPlayerCurrentAdvantage + 1;
                    matchScoreModel.setFirstPlayerAdvantage(newFirstPlayerAdvantage);
                }
            }
        }
    }

    private void scoringSecondPlayerPoints(MatchScoreModel matchScoreModel) {

        int currentPoints = matchScoreModel.getSecondPlayerPoints();

        if (currentPoints == 0) {
            int newPoints = 15;
            matchScoreModel.setSecondPlayerPoints(newPoints);
        }

        if (currentPoints == 15) {
            int newPoints = 30;
            matchScoreModel.setSecondPlayerPoints(newPoints);
        }

        if (currentPoints == 30) {
            int newPoints = 40;
            matchScoreModel.setSecondPlayerPoints(newPoints);
        }

        if (currentPoints == 40) {
            int firstPlayerPoints = matchScoreModel.getFirstPlayerPoints();

            if (firstPlayerPoints < 40) {
                updateSecondPlayerGames(matchScoreModel);
            }

            if (firstPlayerPoints == 40) {
                if (matchScoreModel.getSecondPlayerAdvantage() - matchScoreModel.getFirstPlayerAdvantage() == 2) {
                    updateSecondPlayerGames(matchScoreModel);
                } else {
                    int secondPlayerCurrentAdvantage = matchScoreModel.getSecondPlayerAdvantage();
                    int newSecondPlayerAdvantage = secondPlayerCurrentAdvantage + 1;
                    matchScoreModel.setSecondPlayerAdvantage(newSecondPlayerAdvantage);
                }
            }
        }
    }


    private void updateFirstPlayerGames(MatchScoreModel matchScoreModel) {
        if ((matchScoreModel.getFirstPlayerGames() == 6) && (matchScoreModel.getSecondPlayerGames() == 6)) {
            updateFirstPlayerSets(matchScoreModel);
            return;
        }

        if ((matchScoreModel.getFirstPlayerGames() == 5) && (matchScoreModel.getFirstPlayerGames() - matchScoreModel.getSecondPlayerGames() >= 2)) {
            updateFirstPlayerSets(matchScoreModel);
            return;
        }


        int firstPlayerGames = matchScoreModel.getFirstPlayerGames();
        int newFirstPlayerGames = firstPlayerGames + 1;
        matchScoreModel.setFirstPlayerGames(newFirstPlayerGames);
        matchScoreModel.setFirstPlayerPoints(0);
        matchScoreModel.setSecondPlayerPoints(0);
    }

    private void updateSecondPlayerGames(MatchScoreModel matchScoreModel) {
        if ((matchScoreModel.getSecondPlayerGames() == 6) && (matchScoreModel.getFirstPlayerGames() == 6)) {
            updateSecondPlayerSets(matchScoreModel);
            return;
        }

        if ((matchScoreModel.getSecondPlayerGames() == 5) && (matchScoreModel.getSecondPlayerGames() - matchScoreModel.getFirstPlayerGames() >= 2)) {
            updateSecondPlayerSets(matchScoreModel);
            return;
        }


        int secondPlayerGames = matchScoreModel.getSecondPlayerGames();
        int newSecondPlayerGames = secondPlayerGames + 1;
        matchScoreModel.setSecondPlayerGames(newSecondPlayerGames);
        matchScoreModel.setFirstPlayerPoints(0);
        matchScoreModel.setSecondPlayerPoints(0);
    }


    private void updateFirstPlayerSets(MatchScoreModel matchScoreModel) {
        int firstPlayerCurrentSets = matchScoreModel.getFirstPlayerSets();
        int newFirstPlayerSets = firstPlayerCurrentSets + 1;
        matchScoreModel.setFirstPlayerSets(newFirstPlayerSets);

        matchScoreModel.setFirstPlayerGames(0);
        matchScoreModel.setSecondPlayerGames(0);
        matchScoreModel.setFirstPlayerPoints(0);
        matchScoreModel.setSecondPlayerPoints(0);
    }

    private void updateSecondPlayerSets(MatchScoreModel matchScoreModel) {
        int secondPlayerCurrentSets = matchScoreModel.getSecondPlayerSets();
        int newSecondPlayerSets = secondPlayerCurrentSets + 1;
        matchScoreModel.setSecondPlayerSets(newSecondPlayerSets);

        matchScoreModel.setFirstPlayerGames(0);
        matchScoreModel.setSecondPlayerGames(0);
        matchScoreModel.setFirstPlayerPoints(0);
        matchScoreModel.setSecondPlayerPoints(0);
    }
}