package org.example.tennis;

public class MatchScoreCalculationService {
    private static final int DEFAULT_POINTS = 0;
    private static final int POINTS_FIRST = 15;
    private static final int POINTS_SECOND = 30;
    private static final int POINTS_THIRD = 40;

    private static final int DEFAULT_GAMES = 0;
    private static final int GAMES_TO_WIN_SET = 6;
    private static final int GAMES_WIN_DIFF = 2;

    private static final int TIEBREAK_POINTS_TO_WIN = 7;

    private final MatchStateService matchStateService;

    public MatchScoreCalculationService(MatchStateService matchStateService) {
        this.matchStateService = matchStateService;
    }

    public void scoring(MatchScoreModel match, PlayerSide scorerSide) {
        PlayerSide opponentSide = getOpponentSide(scorerSide);
        MatchScoreModelWrapper scorer = new MatchScoreModelWrapper(match, scorerSide);
        MatchScoreModelWrapper opponent = new MatchScoreModelWrapper(match, opponentSide);

        if (match.isTiebreak()) {
            handleTiebreak(match, scorer, opponent);
            return;
        }
        updatePoints(scorer, opponent, match);
        matchStateService.checkNotFinished(match);
    }

    private void updatePoints(MatchScoreModelWrapper scorer, MatchScoreModelWrapper opponent, MatchScoreModel match) {
        switch (scorer.getPoints()) {
            case DEFAULT_POINTS -> scorer.setPoints(POINTS_FIRST);
            case POINTS_FIRST   -> scorer.setPoints(POINTS_SECOND);
            case POINTS_SECOND  -> scorer.setPoints(POINTS_THIRD);
            case POINTS_THIRD   -> handleFortyPoints(scorer, opponent, match);
        }
    }

    private void handleFortyPoints(MatchScoreModelWrapper scorer, MatchScoreModelWrapper opponent, MatchScoreModel match) {
        if (opponent.getPoints() < POINTS_THIRD) {
            updateGames(scorer, opponent, match);
        } else {
            handleDeuce(scorer, opponent, match);
        }
    }

    private void handleDeuce(MatchScoreModelWrapper scorer, MatchScoreModelWrapper opponent, MatchScoreModel match) {
        if (scorer.isAdvantage()) {
            updateGames(scorer, opponent, match);
            scorer.setAdvantage(false);
            opponent.setAdvantage(false);
        } else if (opponent.isAdvantage()) {
            opponent.setAdvantage(false);
        } else {
            scorer.setAdvantage(true);
        }
    }

    private void updateGames(MatchScoreModelWrapper scorer, MatchScoreModelWrapper opponent, MatchScoreModel match) {
        int scorerGames = scorer.getGames() + 1;
        scorer.setGames(scorerGames);
        int opponentGames = opponent.getGames();

        if (scorerGames == GAMES_TO_WIN_SET && opponentGames == GAMES_TO_WIN_SET) {
            match.setTiebreak(true);
            resetPointsAndAdvantage(match);
            return;
        }

        if (scorerGames >= GAMES_TO_WIN_SET && (scorerGames - opponentGames) >= GAMES_WIN_DIFF) {
            updateSets(scorer, opponent, match);
        } else {
            resetPointsAndAdvantage(match);
        }
    }

    private void handleTiebreak(MatchScoreModel match, MatchScoreModelWrapper scorer, MatchScoreModelWrapper opponent) {
        int scorerPoints = scorer.getPoints() + 1;
        scorer.setPoints(scorerPoints);

        int opponentPoints = opponent.getPoints();
        if (scorerPoints >= TIEBREAK_POINTS_TO_WIN && (scorerPoints - opponentPoints) >= GAMES_WIN_DIFF) {
            updateSets(scorer, opponent, match);
            match.setTiebreak(false);
        }
    }

    private void updateSets(MatchScoreModelWrapper scorer, MatchScoreModelWrapper opponent, MatchScoreModel match) {
        scorer.setSets(scorer.getSets() + 1);

        scorer.setGames(DEFAULT_GAMES);
        opponent.setGames(DEFAULT_GAMES);

        resetPointsAndAdvantage(match);
    }

    private void resetPointsAndAdvantage(MatchScoreModel match) {
        match.setFirstPlayerPoints(DEFAULT_POINTS);
        match.setSecondPlayerPoints(DEFAULT_POINTS);
        match.setFirstPlayerAdvantage(false);
        match.setSecondPlayerAdvantage(false);
    }

    private PlayerSide getOpponentSide(PlayerSide scorerSide) {
        if (scorerSide == PlayerSide.FIRST) {
            return PlayerSide.SECOND;
        } else {
            return PlayerSide.FIRST;
        }
    }
}
