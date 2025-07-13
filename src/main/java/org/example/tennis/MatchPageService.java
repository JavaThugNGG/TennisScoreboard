package org.example.tennis;

import org.hibernate.SessionFactory;

import java.util.List;

public class MatchPageService {
    private final PageResolver pageResolver = new PageResolver();
    private final MatchPageMapper matchPageMapper = new MatchPageMapper();
    private final Pagination pagination = new Pagination();

    MatchService matchService;
    PlayerService playerService;
    MatchFilterService matchFilterService;

    MatchPageService(SessionFactory sessionFactory) {
        this.matchService = new MatchService(sessionFactory);
        this.playerService = new PlayerService(sessionFactory);
        this.matchFilterService = new MatchFilterService(matchService, playerService);
    }

    public MatchPageDto fetch(String page, String playerNameFilter) {
        int currentPage = pageResolver.resolvePage(page);
        int paginationStartIndex = pagination.getStartIndex(currentPage);

        MatchPaginationDto matchesWithCount = matchFilterService.get(playerNameFilter, paginationStartIndex);
        List<MatchEntity> matches = matchesWithCount.getMatches();
        int count = matchesWithCount.getTotalCount();
        int totalPages = pagination.getTotalPages(count);
        return matchPageMapper.convert(matches, currentPage, totalPages);
    }
}
