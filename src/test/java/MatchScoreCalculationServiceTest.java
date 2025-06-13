import org.example.tennis.MatchScoreCalculationService;
import org.example.tennis.MatchScoreModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchScoreCalculationServiceTest {
    @Test
    void scoringFirstPlayerPointsWithoutAdvantageAtDeuceShouldNotEndGame() {
        MatchScoreModel matchScoreModel = new MatchScoreModel(1,2,0,0,0,0,40,40);

        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
        matchScoreCalculationService.scoringFirstPlayer(matchScoreModel);

        assertEquals(1, matchScoreModel.getFirstPlayerAdvantage());
        assertEquals(0, matchScoreModel.getFirstPlayerGames());
    }

    @Test
    void scoringFirstPlayerPointsWithOneAdvantageAtDeuceShouldNotEndGame() {
        MatchScoreModel matchScoreModel = new MatchScoreModel(1, 2, 0, 0, 0, 0, 40, 40);
        matchScoreModel.setFirstPlayerAdvantage(1);

        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
        matchScoreCalculationService.scoringFirstPlayer(matchScoreModel);

        assertEquals(2, matchScoreModel.getFirstPlayerAdvantage());
        assertEquals(0, matchScoreModel.getFirstPlayerGames());
    }

    @Test
    void scoringFirstPlayerPointsWithTwoAdvantagesAtDeuceShouldEndGame() {
        MatchScoreModel matchScoreModel = new MatchScoreModel(1,2,0,0,0,0,40,40);
        matchScoreModel.setFirstPlayerAdvantage(2);

        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
        matchScoreCalculationService.scoringFirstPlayer(matchScoreModel);

        assertEquals(0, matchScoreModel.getFirstPlayerAdvantage());
        assertEquals(1, matchScoreModel.getFirstPlayerGames());
    }

    @Test
    void scoringSecondPlayerPointsWithoutAdvantageAtDeuceShouldNotEndGame() {
        MatchScoreModel matchScoreModel = new MatchScoreModel(1,2,0,0,0,0,40,40);

        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
        matchScoreCalculationService.scoringSecondPlayer(matchScoreModel);

        assertEquals(1, matchScoreModel.getSecondPlayerAdvantage());
        assertEquals(0, matchScoreModel.getSecondPlayerGames());
    }

    @Test
    void scoringSecondPlayerWithOneAdvantagePointsAtDeuceShouldNotEndGame() {
        MatchScoreModel matchScoreModel = new MatchScoreModel(1, 2, 0, 0, 0, 0, 40, 40);
        matchScoreModel.setSecondPlayerAdvantage(1);

        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
        matchScoreCalculationService.scoringSecondPlayer(matchScoreModel);

        assertEquals(2, matchScoreModel.getSecondPlayerAdvantage());
        assertEquals(0, matchScoreModel.getSecondPlayerGames());
    }

    @Test
    void scoringSecondPlayerPointsWithTwoAdvantagesAtDeuceShouldEndGame() {
        MatchScoreModel matchScoreModel = new MatchScoreModel(1,2,0,0,0,0,40,40);
        matchScoreModel.setSecondPlayerAdvantage(2);

        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
        matchScoreCalculationService.scoringSecondPlayer(matchScoreModel);

        assertEquals(0, matchScoreModel.getSecondPlayerAdvantage());
        assertEquals(1, matchScoreModel.getSecondPlayerGames());
    }



}
