import org.example.tennis.MatchScoreCalculationService;
import org.example.tennis.MatchScoreModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchScoreCalculationServiceTest {

    @Test
    void ScoringFirstPlayerPointsWhenScore40_0ShouldUpdateFirstPlayerGamesFrom0To1() {
        MatchScoreModel matchScoreModel = new MatchScoreModel(1,2,0,0,0,0,40,0);

        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
        matchScoreCalculationService.scoringFirstPlayer(matchScoreModel);

        assertEquals(1, matchScoreModel.getFirstPlayerGames());
    }

    @Test
    void ScoringFirstPlayerPointsWhenScore40_15ShouldUpdateFirstPlayerGamesFrom0To1() {
        MatchScoreModel matchScoreModel = new MatchScoreModel(1,2,0,0,0,0,40,15);

        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
        matchScoreCalculationService.scoringFirstPlayer(matchScoreModel);

        assertEquals(1, matchScoreModel.getFirstPlayerGames());
    }

    @Test
    void ScoringFirstPlayerPointsWhenScore40_30ShouldUpdateFirstPlayerGamesFrom0To1() {
        MatchScoreModel matchScoreModel = new MatchScoreModel(1,2,0,0,0,0,40,30);

        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
        matchScoreCalculationService.scoringFirstPlayer(matchScoreModel);

        assertEquals(1, matchScoreModel.getFirstPlayerGames());
    }

    @Test
    void ScoringFirstPlayerPointsWhenScore40_40WithoutAdvantagesShouldNotUpdateFirstPlayerGamesFrom0To1() {
        MatchScoreModel matchScoreModel = new MatchScoreModel(1,2,0,0,0,0,40,40);

        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
        matchScoreCalculationService.scoringFirstPlayer(matchScoreModel);

        assertEquals(0, matchScoreModel.getFirstPlayerGames());
    }

    //тут дописываю

    @Test
    void ScoringFirstPlayerPointsWhenScore40_40WithOneAdvantagesShouldNotUpdateFirstPlayerGamesFrom0To1() {
        MatchScoreModel matchScoreModel = new MatchScoreModel(1,2,0,0,0,0,40,40);
        matchScoreModel.setFirstPlayerAdvantage(1);

        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
        matchScoreCalculationService.scoringFirstPlayer(matchScoreModel);

        assertEquals(0, matchScoreModel.getFirstPlayerGames());
    }

    @Test
    void ScoringFirstPlayerPointsWhenScore40_40WithTwoAdvantagesShouldUpdateFirstPlayerGames() {
        MatchScoreModel matchScoreModel = new MatchScoreModel(1,2,0,0,0,0,40,40);
        matchScoreModel.setFirstPlayerAdvantage(2);

        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
        matchScoreCalculationService.scoringFirstPlayer(matchScoreModel);

        assertEquals(1, matchScoreModel.getFirstPlayerGames());
    }



    @Test
    void ScoringFirstPlayerGamesShouldUpdateFirstPlayerSetsFrom0To1WhileGamesScore6_0() {
        MatchScoreModel matchScoreModel = new MatchScoreModel(1,2,0,0,5,0,40,0);

        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
        matchScoreCalculationService.scoringFirstPlayer(matchScoreModel);

        assertEquals(1, matchScoreModel.getFirstPlayerSets());
    }

    @Test
    void ScoringFirstPlayerGamesShouldUpdateFirstPlayerSetsFrom0To1WhileGamesScore6_1() {
        MatchScoreModel matchScoreModel = new MatchScoreModel(1,2,0,0,5,1,40,0);

        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
        matchScoreCalculationService.scoringFirstPlayer(matchScoreModel);

        assertEquals(1, matchScoreModel.getFirstPlayerSets());
    }

    @Test
    void ScoringFirstPlayerGamesShouldUpdateFirstPlayerSetsFrom0To1WhileGamesScore6_2() {
        MatchScoreModel matchScoreModel = new MatchScoreModel(1,2,0,0,5,2,40,0);

        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
        matchScoreCalculationService.scoringFirstPlayer(matchScoreModel);

        assertEquals(1, matchScoreModel.getFirstPlayerSets());
    }

    @Test
    void ScoringFirstPlayerGamesShouldUpdateFirstPlayerSetsFrom0To1WhileGamesScore6_3() {
        MatchScoreModel matchScoreModel = new MatchScoreModel(1,2,0,0,5,3,40,0);

        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
        matchScoreCalculationService.scoringFirstPlayer(matchScoreModel);

        assertEquals(1, matchScoreModel.getFirstPlayerSets());
    }

    @Test
    void ScoringFirstPlayerGamesShouldUpdateFirstPlayerSetsFrom0To1WhileGamesScore6_4() {
        MatchScoreModel matchScoreModel = new MatchScoreModel(1,2,0,0,5,4,40,0);

        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
        matchScoreCalculationService.scoringFirstPlayer(matchScoreModel);

        assertEquals(1, matchScoreModel.getFirstPlayerSets());
    }

    @Test
    void ScoringFirstPlayerGamesShouldNotUpdateFirstPlayerSetsFrom0To1WhileGamesScore6_5() {
        MatchScoreModel matchScoreModel = new MatchScoreModel(1,2,0,0,5,5,40,0);

        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
        matchScoreCalculationService.scoringFirstPlayer(matchScoreModel);

        assertEquals(0, matchScoreModel.getFirstPlayerSets());
    }

    @Test
    void ScoringFirstPlayerGamesShouldNotUpdateFirstPlayerSetsFrom0To1WhileGamesScore6_6() {
        MatchScoreModel matchScoreModel = new MatchScoreModel(1,2,0,0,5,6,40,0);

        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
        matchScoreCalculationService.scoringFirstPlayer(matchScoreModel);

        assertEquals(0, matchScoreModel.getFirstPlayerSets());
    }

    @Test
    void ScoringFirstPlayerGamesShouldUpdateFirstPlayerSetsFrom0To1WhileGamesScore7_6() {
        MatchScoreModel matchScoreModel = new MatchScoreModel(1,2,0,0,6,6,40,0);

        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
        matchScoreCalculationService.scoringFirstPlayer(matchScoreModel);

        assertEquals(1, matchScoreModel.getFirstPlayerSets());
    }




























}
