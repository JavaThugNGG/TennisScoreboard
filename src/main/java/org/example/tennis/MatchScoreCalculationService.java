package org.example.tennis;

import org.hibernate.Session;

public class MatchScoreCalculationService {

    //реализует логику подсчёта счёта матча по очкам/геймам/сетам

    //для advantage придется заводить мапу или добавть доп поле в MatchScoreModel, что наверное получше!


    //кидать Exception будет когда матч закончится

    //вызывается из сервлета при тыке на скоринг игрока

    //мб в конструктор класса MatchScoreModel пихать в не в параметры метода передавать ???

    public void scoringFirstPlayer(MatchScoreModel matchScoreModel) {

        scoringFirstPlayerGo(matchScoreModel);

        if (matchScoreModel.getFirstPlayerSets() == 2) {                                                         //выигрыш
            if ((matchScoreModel.getSecondPlayerSets() == 0) || (matchScoreModel.getSecondPlayerSets() == 1)) {
                throw new MatchAlreadyFinishedException();
            }
        }
    }

    public void scoringSecondPlayer(MatchScoreModel matchScoreModel) {

        scoringSecondPlayerGo(matchScoreModel);

        if (matchScoreModel.getSecondPlayerSets() == 2) {                                                         //выигрыш
            if ((matchScoreModel.getFirstPlayerSets() == 0) || (matchScoreModel.getFirstPlayerSets() == 1)) {
                throw new MatchAlreadyFinishedException();
            }
        }
    }

    private void scoringFirstPlayerGo(MatchScoreModel matchScoreModel) {

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

        if (currentPoints == 40) {                      //геймы обновляются только тут
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

    private void scoringSecondPlayerGo(MatchScoreModel matchScoreModel) {

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

        if (currentPoints == 40) {                      //геймы обновляются только тут
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
            updateFirstSets(matchScoreModel);
            return;
        }

        if ((matchScoreModel.getFirstPlayerGames() == 5) && (matchScoreModel.getFirstPlayerGames() - matchScoreModel.getSecondPlayerGames() >= 2)) {
            updateFirstSets(matchScoreModel);
            return;
        }


        int firstPlayerGames = matchScoreModel.getFirstPlayerGames();    //обновляем гейм в первого
        int newFirstPlayerGames = firstPlayerGames + 1;
        matchScoreModel.setFirstPlayerGames(newFirstPlayerGames);
        matchScoreModel.setFirstPlayerPoints(0);
        matchScoreModel.setSecondPlayerPoints(0);
    }

    private void updateSecondPlayerGames(MatchScoreModel matchScoreModel) {
        if ((matchScoreModel.getSecondPlayerGames() == 6) && (matchScoreModel.getFirstPlayerGames() == 6)) {
            updateSecondSets(matchScoreModel);
            return;
        }

        if ((matchScoreModel.getSecondPlayerGames() == 5) && (matchScoreModel.getSecondPlayerGames() - matchScoreModel.getFirstPlayerGames() >= 2)) {
            updateSecondSets(matchScoreModel);
            return;
        }


        int secondPlayerGames = matchScoreModel.getSecondPlayerGames();    //обновляем гейм в первого
        int newSecondPlayerGames = secondPlayerGames + 1;
        matchScoreModel.setSecondPlayerGames(newSecondPlayerGames);
        matchScoreModel.setFirstPlayerPoints(0);
        matchScoreModel.setSecondPlayerPoints(0);
    }


    private void updateFirstSets(MatchScoreModel matchScoreModel) {
        int firstPlayerCurrentSets = matchScoreModel.getFirstPlayerSets();
        int newFirstPlayerSets = firstPlayerCurrentSets + 1;
        matchScoreModel.setFirstPlayerSets(newFirstPlayerSets);

        matchScoreModel.setFirstPlayerGames(0);
        matchScoreModel.setSecondPlayerGames(0);
        matchScoreModel.setFirstPlayerPoints(0);
        matchScoreModel.setSecondPlayerPoints(0);
    }

    private void updateSecondSets(MatchScoreModel matchScoreModel) {
        int secondPlayerCurrentSets = matchScoreModel.getSecondPlayerSets();
        int newSecondPlayerSets = secondPlayerCurrentSets + 1;
        matchScoreModel.setSecondPlayerSets(newSecondPlayerSets);

        matchScoreModel.setFirstPlayerGames(0);
        matchScoreModel.setSecondPlayerGames(0);
        matchScoreModel.setFirstPlayerPoints(0);
        matchScoreModel.setSecondPlayerPoints(0);
    }
}