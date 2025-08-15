package org.example.tennis.processor;

import org.example.tennis.parser.PageParser;
import org.example.tennis.validator.PageValidator;

public class PageProcessor {
    private static final int DEFAULT_PAGE_INDEX = 1;
    private final PageValidator pageValidator = new PageValidator();
    private final PageParser pageParser = new PageParser();

    public int determinePage(String page) {
        if (isPageProvided(page)) {
            int pageInt = pageParser.parsePage(page);
            pageValidator.validatePage(pageInt);
            return pageInt;
        }
        return DEFAULT_PAGE_INDEX;
    }

    private boolean isPageProvided(String page) {
        return page != null;
    }
}
