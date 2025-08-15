package org.example.tennis.service;

import org.example.tennis.entity.PlayerEntity;
import org.example.tennis.validator.PlayerValidator;
import org.example.tennis.dto.MatchesSummaryDto;

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

