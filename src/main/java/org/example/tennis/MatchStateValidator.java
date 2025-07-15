package org.example.tennis;

public class MatchStateValidator {
    private static final int WINNING_SETS = 2;
    private static final int MAX_OPPONENT_SETS_FOR_FINISHED_MATCH = 1;

    public void validateMatchFinished(MatchScoreModel matchScoreModel) {
        if ((matchScoreModel.getFirstPlayerSets() == WINNING_SETS && matchScoreModel.getSecondPlayerSets() <= MAX_OPPONENT_SETS_FOR_FINISHED_MATCH) ||
                (matchScoreModel.getSecondPlayerSets() == WINNING_SETS && matchScoreModel.getFirstPlayerSets() <= MAX_OPPONENT_SETS_FOR_FINISHED_MATCH)) {
            throw new MatchAlreadyFinishedException();
        }
    }
}
