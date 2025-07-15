package org.example.tennis;

public class MatchFilterService {
    private final MatchService matchService;
    private final PlayerService playerService;

    public MatchFilterService(MatchService matchService, PlayerService playerService) {
        this.matchService = matchService;
        this.playerService = playerService;
    }

    public MatchPaginationDto get(String playerNameFilter, int startIndex) {
        if (playerNameFilter == null) {
            return getWithoutFilter(startIndex);
        } else {
            return getWithFilter(playerNameFilter, startIndex);
        }
    }

    private MatchPaginationDto getWithoutFilter(int startIndex) {
        return new MatchPaginationDto(matchService.getPage(startIndex),
                (int) matchService.count()
        );
    }

    private MatchPaginationDto getWithFilter(String playerNameFilter, int startIndex) {
        PlayerEntity player = playerService.getByName(playerNameFilter);
        return new MatchPaginationDto(
                matchService.getPageWithPlayerFilter(player, startIndex),
                (int) matchService.countWithPlayerFilter(player)
        );
    }
}

