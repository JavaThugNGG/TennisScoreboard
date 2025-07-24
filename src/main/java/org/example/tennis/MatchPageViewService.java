package org.example.tennis;

import java.util.List;

public class MatchPageViewService {
    public final int MATCHES_PER_PAGE = 5;

    private final PageProcessor pageProcessor;
    private final MatchPageViewDtoBuilder matchPageViewDtoBuilder;
    private final MatchPageViewCalculator matchPageViewCalculator = new MatchPageViewCalculator(MATCHES_PER_PAGE);;
    private final MatchesSummaryService matchesSummaryService;
    private final PlayerValidator playerValidator;

    MatchPageViewService(PageProcessor pageProcessor, MatchesSummaryService matchesSummaryService, MatchPageViewDtoBuilder matchPageViewDtoBuilder, PlayerValidator playerValidator) {
        this.pageProcessor = pageProcessor;
        this.matchesSummaryService = matchesSummaryService;
        this.matchPageViewDtoBuilder = matchPageViewDtoBuilder;
        this.playerValidator = playerValidator;
    }

    public MatchPageViewDto getPage(String page, String playerNameFilter) {
        int currentPage = pageProcessor.determinePage(page);
        int paginationStartIndex = matchPageViewCalculator.getStartIndex(currentPage);

        MatchesSummaryDto matchesWithCount;
        if (playerNameFilter == null) {
            matchesWithCount = matchesSummaryService.getWithoutFilter(paginationStartIndex);
        } else {
            playerValidator.validateNameFilter(playerNameFilter);
            matchesWithCount = matchesSummaryService.getWithFilter(playerNameFilter, paginationStartIndex);
        }

        List<MatchEntity> matches = matchesWithCount.getMatches();
        int count = matchesWithCount.getTotalCount();
        int totalPages = matchPageViewCalculator.getTotalPages(count);
        return matchPageViewDtoBuilder.build(matches, currentPage, totalPages);
    }
}
