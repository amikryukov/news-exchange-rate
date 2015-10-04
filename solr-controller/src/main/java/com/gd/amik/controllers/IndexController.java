package com.gd.amik.controllers;

import com.gd.amik.controllers.news.guardian.GuardianNewsLoader;
import com.gd.amik.controllers.news.guardian.PieceOfNews;
import com.gd.amik.controllers.news.guardian.SetOfNews;
import com.gd.amik.controllers.series.SeriesLoader;
import com.gd.amik.controllers.series.SingleSeriesAnalyzer;
import com.gd.amik.controllers.series.util.TimeSerie;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Loads news, generate weight field, index to solr
 */

// commented while dialling with other controller
@Controller
public class IndexController {

    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    // hardcodded date range
    private static final long DATE_RANGE = 5 * 60 * 1_000L; // 5 minutes

    // todo: make it configurable
    private GuardianNewsLoader newsLoader;
    private SingleSeriesAnalyzer seriesAnalizer;

    @Autowired
    private HttpSolrClient httpSolrClient;

    @Autowired
    private SimpleDateFormat dateFormat;

    public IndexController() throws IOException, ParseException {

        newsLoader = new GuardianNewsLoader("test");
        init();
    }

    @ResponseBody
    @RequestMapping("/deleteAll")
    public synchronized String doDeleteAll() throws IOException, SolrServerException {
        try {
            log.info("Start deleteAll ...");

            // delete everything
            httpSolrClient.deleteByQuery("*:*");
            httpSolrClient.commit();
            return "OK";
        } catch (Exception e) {
            log.error("Exception while indexing", e);
            return "NOT_OK";
        }
    }

    @ResponseBody
    @RequestMapping("/index")
    public synchronized String doIndex() {

        try {
            log.info("Start indexing ...");

            // hardcodded 2 mounths
            String startDate = "2015-08-01";
            //String startDate = "2015-09-25"; // 3 days for testing
            String endDate = "2015-09-29";
            int pageSize = 200; // max page size guardian provides.
            int currentPage = 1;
            int numberOfPages = currentPage;

            int docNumber = 1;

            while (currentPage <= numberOfPages) {
                SetOfNews setOfNews = newsLoader.getSetOfNews(startDate, endDate, pageSize, currentPage);
                numberOfPages = setOfNews.getPages();

                for (PieceOfNews piece : setOfNews.getNews()) {
                    SolrInputDocument doc = new SolrInputDocument();
                    doc.addField("id", docNumber++);
                    doc.addField("title", piece.getTitle());
                    doc.addField("date", piece.getDate());
                    doc.addField("web_url", piece.getWebURL());
                    doc.addField("topic", piece.getTopic());
                    doc.addField("thumbnail", piece.getThumbnail());
                    for (String keyWord : piece.getKeywords()) {
                        doc.addField("keyword", keyWord);
                    }
                    doc.addField("eur_us_weight", analizeTheNew(piece));
                    httpSolrClient.add(doc);
                }

                log.info("Loaded: news.size.loaded=" + setOfNews.getNews().size() +
                        ", curPage=" + setOfNews.getCurrentPage() + ", pages=" + setOfNews.getPages() +
                        ", total=" + setOfNews.getTotal());

                currentPage++;
            }

            httpSolrClient.commit();
            log.info("All documents were loadded and committed.");
            return "OK";
        } catch (Exception e) {
            log.error("Exception while indexing", e);
            return "NOT_OK";
        }
    }

    private void init() throws IOException, ParseException {
        if (seriesAnalizer == null) {

            log.info("Start loading series analizer ...");
            SeriesLoader loader = new SeriesLoader();
            TimeSerie timeSerie = loader.load("src/main/resources/DAT_NT_EURUSD_T_ASK_201508.csv"); // ~ 26mb
            TimeSerie timeSerie09 = loader.load("src/main/resources/DAT_NT_EURUSD_T_ASK_201509.csv"); // ~ 25mb
            timeSerie.addAllPoints(timeSerie09.getAllPoints());
            seriesAnalizer = new SingleSeriesAnalyzer("eur_us", timeSerie);
            log.info("series analizer was loaded successfully");
        }
    }

    private double analizeTheNew(PieceOfNews piece) throws ParseException {
        String dateStr = piece.getDate();
        long date = dateFormat.parse(dateStr).getTime();
        double preResult = seriesAnalizer.calculateActivity(date - DATE_RANGE, date + DATE_RANGE);
        if (preResult == 0D) {
            return Double.MIN_NORMAL;
        }
        return preResult;
    }
}
