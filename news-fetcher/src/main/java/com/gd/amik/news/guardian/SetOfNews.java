package com.gd.amik.news.guardian;

import java.util.ArrayList;
import java.util.List;

public class SetOfNews {

    private List<PieceOfNews> news = new ArrayList<PieceOfNews>();
    private int pages;
    private int currentPage;
    private int total;

    SetOfNews(int pages, int currentPage, int total) {
        this.pages = pages;
        this.currentPage = currentPage;
        this.total = total;
    }

    void addNew(PieceOfNews theNew) {
        news.add(theNew);
    }

    public List<PieceOfNews> getNews() {
        return news;
    }

    public int getPages() {
        return pages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotal() {
        return total;
    }
}
