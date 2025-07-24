package org.example.tennis;

public class MatchesSummaryService {
    private final static int MATCHES_PER_PAGE = 5;

    private final MatchService matchService;
    private final PlayerService playerService;
    private final PlayerValidator playerValidator;

    public MatchesSummaryService(MatchService matchService, PlayerService playerService, PlayerValidator playerValidator) {
        this.matchService = matchService;
        this.playerService = playerService;
        this.playerValidator = playerValidator;
    }

    public MatchesSummaryDto get(String playerNameFilter, int paginationStartIndex) {
        if (playerNameFilter == null) {
            return getWithoutFilter(paginationStartIndex);
        }
        playerValidator.validateNameFilter(playerNameFilter);
        return getWithFilter(playerNameFilter, paginationStartIndex);
    }

    public MatchesSummaryDto getWithoutFilter(int paginationStartIndex) {
        return new MatchesSummaryDto(matchService.getPage(MATCHES_PER_PAGE, paginationStartIndex),
                (int) matchService.count()
        );
    }

    public MatchesSummaryDto getWithFilter(String playerNameFilter, int paginationStartIndex) {
        playerValidator.validateNameFilter(playerNameFilter);

        PlayerEntity player = playerService.getByName(playerNameFilter);

        return new MatchesSummaryDto(
                matchService.getPageWithPlayerFilter(player, MATCHES_PER_PAGE, paginationStartIndex),
                (int) matchService.countWithPlayerFilter(player)
        );
    }
}

