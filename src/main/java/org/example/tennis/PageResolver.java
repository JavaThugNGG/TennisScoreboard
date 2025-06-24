package org.example.tennis;

public class PageResolver {
    private static final int DEFAULT_PAGE_INDEX = 1;
    PageValidator pageValidator = new PageValidator();
    PageParser pageParser = new PageParser();

    public int resolvePage(String page) {
        if (isPageProvided(page)) {
            pageValidator.validate(page);
            return pageParser.parsePage(page);
        }
        return DEFAULT_PAGE_INDEX;
    }

    private boolean isPageProvided(String page) {
        return page != null;
    }
}
