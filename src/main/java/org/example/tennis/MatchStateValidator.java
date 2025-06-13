package org.example.tennis;

public class MatchStateValidator {
    public void validateMatchNotFinished(MatchScoreModel matchScoreModel) {
        if ((matchScoreModel.getFirstPlayerSets() == 2 && matchScoreModel.getSecondPlayerSets() <= 1) ||
                (matchScoreModel.getSecondPlayerSets() == 2 && matchScoreModel.getFirstPlayerSets() <= 1)) {
            throw new MatchAlreadyFinishedException();
        }

    }



}
