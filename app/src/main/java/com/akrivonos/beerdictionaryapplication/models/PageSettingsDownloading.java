package com.akrivonos.beerdictionaryapplication.models;

public class PageSettingsDownloading {
    private final int currentPage;
    private final int pagesAmount;

    public PageSettingsDownloading(int currentPage, int pagesAmount) {
        this.currentPage = currentPage;
        this.pagesAmount = pagesAmount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPagesAmount() {
        return pagesAmount;
    }

}
