package org.example.tennis;

public class Pagination {
    private final int matchesPerPage;

    public Pagination() {
        this.matchesPerPage = 5;
    }

    public int getStartIndex(int currentPage) {
        return (currentPage - 1) * matchesPerPage;
    }

    public int getTotalPages(int totalItems) {
        return (int) Math.ceil((double) totalItems / matchesPerPage);
    }
}
