package org.example.tennis;

import java.util.Collections;

public class MatchesSummaryService {
    private final int MATCHES_PER_PAGE;

    private final MatchService matchService;
    private final PlayerService playerService;
    private final PlayerNameFilterValidator playerNameFilterValidator;

    public MatchesSummaryService(MatchService matchService, PlayerService playerService, int matchesPerPage,PlayerNameFilterValidator playerNameFilterValidator) {
        this.matchService = matchService;
        this.playerService = playerService;
        this.MATCHES_PER_PAGE = matchesPerPage;
        this.playerNameFilterValidator = playerNameFilterValidator;
    }

    public MatchesSummaryDto get(String playerNameFilter, int paginationStartIndex) {
        if (playerNameFilter == null) {
            return getWithoutFilter(paginationStartIndex);
        } else {
            return getWithFilter(playerNameFilter, paginationStartIndex);
        }
    }

    private MatchesSummaryDto getWithoutFilter(int paginationStartIndex) {
        return new MatchesSummaryDto(matchService.getPage(MATCHES_PER_PAGE, paginationStartIndex),
                (int) matchService.count()
        );
    }

    private MatchesSummaryDto getWithFilter(String playerNameFilter, int paginationStartIndex) {
        playerNameFilterValidator.validateFilter(playerNameFilter);

        PlayerEntity player = playerService.getByName(playerNameFilter);

        return new MatchesSummaryDto(
                matchService.getPageWithPlayerFilter(player, MATCHES_PER_PAGE, paginationStartIndex),
                (int) matchService.countWithPlayerFilter(player)
        );
    }
}

