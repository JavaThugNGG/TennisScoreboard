package org.example.tennis;

import java.util.List;

public class MatchPageViewService {
    public final int MATCHES_PER_PAGE = 5;

    private final PageProcessor pageProcessor = new PageProcessor();   //никакой сервис не должен сам их создавать
    private final MatchPageViewMapper matchPageViewMapper = new MatchPageViewMapper();
    private final MatchPageViewCalculator matchPageViewCalculator = new MatchPageViewCalculator(MATCHES_PER_PAGE);
    private final MatchesSummaryService matchesSummaryService;
    private final PlayerValidator playerValidator = new PlayerValidator();

    MatchPageViewService(MatchesSummaryService matchesSummaryService) {
        this.matchesSummaryService = matchesSummaryService;
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
        return matchPageViewMapper.toDto(matches, currentPage, totalPages);
    }
}
