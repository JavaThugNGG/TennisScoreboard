package org.example.tennis;

import org.hibernate.SessionFactory;

import java.util.List;

public class MatchPageViewService {
    private final int MATCHES_PER_PAGE = 5;

    private final PageProcessor pageProcessor = new PageProcessor();
    private final MatchPageViewMapper matchPageViewMapper = new MatchPageViewMapper();
    private final PaginationCalculator paginationCalculator = new PaginationCalculator(MATCHES_PER_PAGE);
    private final MatchesSummaryService matchesSummaryService;

    MatchPageViewService(SessionFactory sessionFactory) {
        MatchDao matchDao = new MatchDao();
        MatchService matchService = new MatchService(sessionFactory, matchDao);
        PlayerService playerService = new PlayerService(sessionFactory);
        this.matchesSummaryService = new MatchesSummaryService(matchService, playerService, MATCHES_PER_PAGE);
    }

    public MatchPageViewDto getPage(String page, String playerNameFilter) {
        int currentPage = pageProcessor.determinePage(page);
        int paginationStartIndex = paginationCalculator.getStartIndex(currentPage);

        MatchesSummaryDto matchesWithCount = matchesSummaryService.get(playerNameFilter, paginationStartIndex);
        List<MatchEntity> matches = matchesWithCount.getMatches();
        int count = matchesWithCount.getTotalCount();
        int totalPages = paginationCalculator.getTotalPages(count);
        return matchPageViewMapper.toDto(matches, currentPage, totalPages);
    }
}
