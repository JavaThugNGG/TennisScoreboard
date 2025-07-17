package org.example.tennis;

public class MatchScoreCalculationService {
    private static final int DEFAULT_POINTS = 0;
    private static final int POINTS_FIRST = 15;
    private static final int POINTS_SECOND = 30;
    private static final int POINTS_THIRD = 40;

    private static final int DEFAULT_GAMES = 0;
    private static final int GAMES_TO_WIN_SET = 6;

    private static final int DEFAULT_ADVANTAGE = 0;
    private static final int ADVANTAGE_WIN_DIFF = 2;

    private static final int TIEBREAK_POINTS_TO_WIN = 7;

    private final MatchStateService matchStateService = new MatchStateService();

    public void scoring(MatchScoreModel match, PlayerSide scorerSide) {
        PlayerScoreModel scorer = new PlayerScoreModel(match, scorerSide);
        PlayerScoreModel opponent = new PlayerScoreModel(match, getOpponentSide(scorerSide));

        if (match.isTiebreak()) {
            handleTiebreak(match, scorer, opponent);
            return;
        }
        updatePoints(scorer, opponent, match);
        matchStateService.checkNotFinished(match);
    }

    private void updatePoints(PlayerScoreModel scorer, PlayerScoreModel opponent, MatchScoreModel match) {
        switch (scorer.getPoints()) {
            case DEFAULT_POINTS -> scorer.setPoints(POINTS_FIRST);
            case POINTS_FIRST   -> scorer.setPoints(POINTS_SECOND);
            case POINTS_SECOND  -> scorer.setPoints(POINTS_THIRD);
            case POINTS_THIRD   -> handleFortyPoints(scorer, opponent, match);
        }
    }

    private void handleFortyPoints(PlayerScoreModel scorer, PlayerScoreModel opponent, MatchScoreModel match) {
        if (opponent.getPoints() < POINTS_THIRD) {
            updateGames(scorer, opponent, match);
        } else {
            int advantageDiff = scorer.getAdvantage() - opponent.getAdvantage();
            if (advantageDiff == ADVANTAGE_WIN_DIFF) {
                updateGames(scorer, opponent, match);
            } else {
                scorer.setAdvantage(scorer.getAdvantage() + 1);
            }
        }
    }

    private void updateGames(PlayerScoreModel scorer, PlayerScoreModel opponent, MatchScoreModel match) {
        int scorerGames = scorer.getGames() + 1;
        scorer.setGames(scorerGames);
        int opponentGames = opponent.getGames();

        if (scorerGames == GAMES_TO_WIN_SET && opponentGames == GAMES_TO_WIN_SET) {
            match.setTiebreak(true);
            resetPointsAndAdvantage(match);
            return;
        }

        if (scorerGames >= GAMES_TO_WIN_SET && (scorerGames - opponentGames) >= ADVANTAGE_WIN_DIFF) {
            updateSets(scorer, opponent, match);
        } else {
            resetPointsAndAdvantage(match);
        }
    }

    private void handleTiebreak(MatchScoreModel match, PlayerScoreModel scorer, PlayerScoreModel opponent) {
        int scorerPoints = scorer.getPoints() + 1;
        scorer.setPoints(scorerPoints);

        int opponentPoints = opponent.getPoints();
        if (scorerPoints >= TIEBREAK_POINTS_TO_WIN && (scorerPoints - opponentPoints) >= ADVANTAGE_WIN_DIFF) {
            updateSets(scorer, opponent, match);
            match.setTiebreak(false);
        }
    }

    private void updateSets(PlayerScoreModel scorer, PlayerScoreModel opponent, MatchScoreModel match) {
        scorer.setSets(scorer.getSets() + 1);

        scorer.setGames(DEFAULT_GAMES);
        opponent.setGames(DEFAULT_GAMES);

        resetPointsAndAdvantage(match);
    }

    private void resetPointsAndAdvantage(MatchScoreModel match) {
        match.setFirstPlayerPoints(DEFAULT_POINTS);
        match.setSecondPlayerPoints(DEFAULT_POINTS);
        match.setFirstPlayerAdvantage(DEFAULT_ADVANTAGE);
        match.setSecondPlayerAdvantage(DEFAULT_ADVANTAGE);
    }

    private PlayerSide getOpponentSide(PlayerSide scorerSide) {
        if (scorerSide == PlayerSide.FIRST) {
            return PlayerSide.SECOND;
        } else {
            return PlayerSide.FIRST;
        }
    }
}
