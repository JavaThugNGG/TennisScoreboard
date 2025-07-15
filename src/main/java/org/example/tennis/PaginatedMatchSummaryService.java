package org.example.tennis;

public class PaginatedMatchSummaryService {
    private final int MATCHES_PER_PAGE;

    private final MatchService matchService;
    private final PlayerService playerService;

    public PaginatedMatchSummaryService(MatchService matchService, PlayerService playerService, int matchesPerPage) {
        this.matchService = matchService;
        this.playerService = playerService;
        this.MATCHES_PER_PAGE = matchesPerPage;
    }

    public MatchSummaryDto get(String playerNameFilter, int paginationStartIndex) {
        if (playerNameFilter == null) {
            return getWithoutFilter(paginationStartIndex);
        } else {
            return getWithFilter(playerNameFilter, paginationStartIndex);
        }
    }

    private MatchSummaryDto getWithoutFilter(int paginationStartIndex) {
        return new MatchSummaryDto(matchService.getPage(MATCHES_PER_PAGE, paginationStartIndex),
                (int) matchService.count()
        );
    }

    private MatchSummaryDto getWithFilter(String playerNameFilter, int paginationStartIndex) {
        PlayerEntity player = playerService.getByName(playerNameFilter);
        return new MatchSummaryDto(
                matchService.getPageWithPlayerFilter(player, MATCHES_PER_PAGE, paginationStartIndex),
                (int) matchService.countWithPlayerFilter(player)
        );
    }
}

