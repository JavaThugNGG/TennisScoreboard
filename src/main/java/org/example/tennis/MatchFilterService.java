package org.example.tennis;

public class MatchFilterService {
    private final MatchService matchService;
    private final PlayerService playerService;

    public MatchFilterService(MatchService matchService, PlayerService playerService) {
        this.matchService = matchService;
        this.playerService = playerService;
    }

    public MatchPaginationDto get(String playerNameFilter, int paginationStartIndex) {
        if (playerNameFilter == null) {
            return new MatchPaginationDto(matchService.getPage(paginationStartIndex),
                    (int) matchService.count()
            );
        } else {
            PlayerEntity player = playerService.getByName(playerNameFilter);
            return new MatchPaginationDto(
                    matchService.getPageWithPlayerFilter(player, paginationStartIndex),
                    (int) matchService.countWithPlayerFilter(player)
            );
        }
    }
}

