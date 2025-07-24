package org.example.tennis;

public class MatchPageViewCalculator {
    private final int matchesPerPage;

    public MatchPageViewCalculator(int matchesPerPage) {
        this.matchesPerPage = matchesPerPage;
    }

    public int getStartIndex(int currentPage) {
        return (currentPage - 1) * matchesPerPage;
    }

    public int getTotalPages(int totalItems) {
        int totalPages = (int) Math.ceil((double) totalItems / matchesPerPage);
        return Math.max(1, totalPages);
    }
}
