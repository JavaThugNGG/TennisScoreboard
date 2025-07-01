package org.example.tennis;

import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Map;

public class MatchPageHandler {
    private static final int MATCHES_PER_PAGE = 5;

    public Map<String, String> handleRequest(HttpServletRequest request, SessionFactory sessionFactory, String page, String playerNameFilter) {
        PageResolver pageResolver = new PageResolver();
        MatchService matchService = new MatchService(sessionFactory);
        PlayerService playerService = new PlayerService(sessionFactory);
        MatchAttributePopulator matchAttributePopulator = new MatchAttributePopulator();

        int currentPage = pageResolver.resolvePage(page);
        int paginationStartIndex = (currentPage - 1) * MATCHES_PER_PAGE;

        long totalMatches;
        List<MatchEntity> currentMatches;

        if (playerNameFilter == null) {
            currentMatches = matchService.getPage(paginationStartIndex);
            totalMatches = matchService.count();
        } else {
            PlayerEntity player = playerService.getByName(playerNameFilter);
            currentMatches = matchService.getPageWithPlayerFilter(player, paginationStartIndex);
            totalMatches = matchService.countWithPlayerFilter(player);
        }

        int totalPages = countTotalPages(totalMatches);

        return matchAttributePopulator.populate(request, currentMatches, currentPage, totalPages);
    }

    private int countTotalPages(long matches) {
        return (int) Math.ceil((double) matches / MATCHES_PER_PAGE);
    }
}
