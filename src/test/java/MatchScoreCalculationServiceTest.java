import org.example.tennis.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

public class MatchScoreCalculationServiceTest {
    private final MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();

    @ParameterizedTest
    @EnumSource(PlayerSide.class)
    void scoringPointsFrom0ShouldUpdatePointsFrom0To15(PlayerSide scorerSide) {
        MatchScoreModel match = new MatchScoreModel(1, 2);
        PlayerScoreModel scorer = new PlayerScoreModel(match, scorerSide);

        matchScoreCalculationService.scoring(match, scorerSide);

        assertEquals(15, scorer.getPoints());
    }

    @ParameterizedTest
    @EnumSource(PlayerSide.class)
    void scoringPointsFrom15ShouldUpdatePointsFrom15To30(PlayerSide scorerSide) {
        MatchScoreModel match = new MatchScoreModel(1, 2);
        PlayerScoreModel scorer = new PlayerScoreModel(match, scorerSide);

        scorer.setPoints(15);
        matchScoreCalculationService.scoring(match, scorerSide);

        assertEquals(30, scorer.getPoints());
    }

    @ParameterizedTest
    @EnumSource(PlayerSide.class)
    void scoringPointsFrom30ShouldUpdatePointsFrom30To40(PlayerSide scorerSide) {
        MatchScoreModel match = new MatchScoreModel(1, 2);
        PlayerScoreModel scorer = new PlayerScoreModel(match, scorerSide);

        scorer.setPoints(30);
        matchScoreCalculationService.scoring(match, scorerSide);

        assertEquals(40, scorer.getPoints());
    }

    @ParameterizedTest
    @EnumSource(PlayerSide.class)
    void scoringPointsFrom40ShouldIncreaseGames(PlayerSide scorerSide) {
        MatchScoreModel match = new MatchScoreModel(1, 2);
        PlayerScoreModel scorer = new PlayerScoreModel(match, scorerSide);

        scorer.setPoints(40);
        matchScoreCalculationService.scoring(match, scorerSide);

        assertEquals(1, scorer.getGames());
    }

    @ParameterizedTest
    @EnumSource(PlayerSide.class)
    void scoringAtDeuceShouldNotIncreaseGames(PlayerSide scorerSide) {
        MatchScoreModel match = new MatchScoreModel(1, 2);
        PlayerScoreModel scorer = new PlayerScoreModel(match, scorerSide);
        PlayerSide opponentSide = getOpponentSide(scorerSide);
        PlayerScoreModel opponent = new PlayerScoreModel(match, opponentSide);

        scorer.setPoints(40);
        opponent.setPoints(40);

        matchScoreCalculationService.scoring(match, scorerSide);

        assertEquals(0, scorer.getGames());
    }

    @ParameterizedTest
    @EnumSource(PlayerSide.class)
    void scoringAtDeuceWithNoAdvantageShouldNotIncreaseGames(PlayerSide scorerSide) {
        MatchScoreModel match = new MatchScoreModel(1, 2);
        PlayerScoreModel scorer = new PlayerScoreModel(match, scorerSide);
        PlayerSide opponentSide = getOpponentSide(scorerSide);
        PlayerScoreModel opponent = new PlayerScoreModel(match, opponentSide);

        scorer.setPoints(40);
        opponent.setPoints(40);
        scorer.setAdvantage(false);

        matchScoreCalculationService.scoring(match, scorerSide);

        assertEquals(0, scorer.getGames());
    }

    @ParameterizedTest
    @EnumSource(PlayerSide.class)
    void scoringAtDeuceWithAdvantageShouldWinGame(PlayerSide scorerSide) {
        MatchScoreModel match = new MatchScoreModel(1, 2);
        PlayerScoreModel scorer = new PlayerScoreModel(match, scorerSide);
        PlayerSide opponentSide = getOpponentSide(scorerSide);
        PlayerScoreModel opponent = new PlayerScoreModel(match, opponentSide);

        scorer.setPoints(40);
        opponent.setPoints(40);
        scorer.setAdvantage(true);

        matchScoreCalculationService.scoring(match, scorerSide);

        assertEquals(1, scorer.getGames());
    }

    @ParameterizedTest
    @EnumSource(PlayerSide.class)
    void scoringAtDeuceWithAdvantageShouldResetAdvantages(PlayerSide scorerSide) {
        MatchScoreModel match = new MatchScoreModel(1, 2);
        PlayerScoreModel scorer = new PlayerScoreModel(match, scorerSide);
        PlayerSide opponentSide = getOpponentSide(scorerSide);
        PlayerScoreModel opponent = new PlayerScoreModel(match, opponentSide);

        scorer.setPoints(40);
        opponent.setPoints(40);
        scorer.setAdvantage(true);

        matchScoreCalculationService.scoring(match, scorerSide);

        assertFalse(scorer.isAdvantage());
        assertFalse(opponent.isAdvantage());
    }

    @ParameterizedTest
    @EnumSource(PlayerSide.class)
    void scoringGamesAt6_5ShouldStartTiebreak(PlayerSide scorerSide) {
        MatchScoreModel match = new MatchScoreModel(1, 2);
        PlayerScoreModel scorer = new PlayerScoreModel(match, scorerSide);
        PlayerSide opponentSide = getOpponentSide(scorerSide);
        PlayerScoreModel opponent = new PlayerScoreModel(match, opponentSide);

        scorer.setPoints(40);
        opponent.setPoints(0);
        scorer.setGames(5);
        opponent.setGames(6);

        matchScoreCalculationService.scoring(match, scorerSide);

        assertTrue(match.isTiebreak());
    }

    @ParameterizedTest
    @EnumSource(PlayerSide.class)
    void scoringGamesAt5_5ShouldNotStartTiebreak(PlayerSide scorerSide) {
        MatchScoreModel match = new MatchScoreModel(1, 2);
        PlayerScoreModel scorer = new PlayerScoreModel(match, scorerSide);
        PlayerSide opponentSide = getOpponentSide(scorerSide);
        PlayerScoreModel opponent = new PlayerScoreModel(match, opponentSide);

        scorer.setPoints(40);
        opponent.setPoints(0);
        scorer.setGames(5);
        opponent.setGames(5);

        matchScoreCalculationService.scoring(match, scorerSide);

        assertFalse(match.isTiebreak());
    }

    @ParameterizedTest
    @EnumSource(PlayerSide.class)
    void scoringGamesAt6_4ShouldIncreaseSets(PlayerSide scorerSide) {
        MatchScoreModel match = new MatchScoreModel(1, 2);
        PlayerScoreModel scorer = new PlayerScoreModel(match, scorerSide);
        PlayerSide opponentSide = getOpponentSide(scorerSide);
        PlayerScoreModel opponent = new PlayerScoreModel(match, opponentSide);

        scorer.setPoints(40);
        opponent.setPoints(0);
        scorer.setGames(5);
        opponent.setGames(4);
        scorer.setSets(0);

        matchScoreCalculationService.scoring(match, scorerSide);

        assertEquals(1, scorer.getSets());
    }

    private PlayerSide getOpponentSide(PlayerSide side) {
        return (side == PlayerSide.FIRST) ? PlayerSide.SECOND : PlayerSide.FIRST;
    }
}
