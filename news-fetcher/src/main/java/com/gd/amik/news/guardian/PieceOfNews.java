package com.gd.amik.news.guardian;

import java.util.List;

/**
 * Describe single piece of news according to guardianapis.com API.
 */
public class PieceOfNews {

    private final String topic;

    private final String title;

    private final String main;

    private final List<String> keywords;

    private final String date;

    /**
     * Used by JaxB
     */
    private PieceOfNews() {
        this.topic = null;
        this.title = null;
        this.main = null;
        this.keywords = null;
        this.date = null;
    }

    public PieceOfNews(String topic, String title, String main, List<String> keywords, String date) {
        this.topic = topic;
        this.title = title;
        this.main = main;
        this.keywords = keywords;
        this.date = date;
    }

    public String getTopic() {
        return topic;
    }

    public String getTitle() {
        return title;
    }

    public String getMain() {
        return main;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public String getTime() {
        return date;
    }

    @Override
    public String toString() {
        return "PieceOfNews{" +
                "topic='" + topic + '\'' +
                ", title='" + title + '\'' +
                ", main='" + main + '\'' +
                ", keywords=" + keywords +
                ", date=" + date +
                '}';
    }
}
