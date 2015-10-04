package com.gd.amik.controllers.news.guardian;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SetOfNews implements Serializable {

    private List<PieceOfNews> news = new ArrayList<PieceOfNews>();
    private int pages;
    private int currentPage;
    private int total;

    public SetOfNews(int pages, int currentPage, int total) {
        this.pages = pages;
        this.currentPage = currentPage;
        this.total = total;
    }

    public void addNew(PieceOfNews theNew) {
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
