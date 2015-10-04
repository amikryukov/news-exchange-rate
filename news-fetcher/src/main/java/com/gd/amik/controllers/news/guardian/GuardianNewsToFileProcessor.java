package com.gd.amik.controllers.news.guardian;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Save all news from Guardian to file.
 */
public class GuardianNewsToFileProcessor {

    public static void main(String[] args) throws IOException {
        GuardianNewsLoader newsLoader = new GuardianNewsLoader("test");



        //String startDate = "2015-08-01";
        String startDate = "2015-08-01";
        String endDate = "2015-09-29";
        int pageSize = 200; // max page size guardian provides.
        int currentPage = 1;
        int numberOfPages = currentPage; //

        while (currentPage <= numberOfPages) {
            SetOfNews setOfNews = newsLoader.getSetOfNews(startDate, endDate, pageSize, currentPage);

            FileWriter fw = new FileWriter("news_archive/guardians_news_" + setOfNews.getCurrentPage() + "_" +
                    setOfNews.getPages() +
                    ".csv");

            for (PieceOfNews theNew : setOfNews.getNews()) {
                fw.write(theNew.getDate() + "");
                fw.write("\t");
                fw.write(theNew.getTopic());
                fw.write("\t");
                fw.write(theNew.getTitle());
                fw.write("\t");
                fw.write(theNew.getKeywords() + "");
                fw.write("\n");
            }

            fw.flush();
            fw.close();

            // todo: add logger
            System.out.println("Loaded: news.size.loaded=" + setOfNews.getNews().size() +
                    ", curPage=" + setOfNews.getCurrentPage() + ", pages=" + setOfNews.getPages() +
                    ", total=" + setOfNews.getTotal());

            numberOfPages = setOfNews.getPages();
            currentPage++;
        }
    }
}
