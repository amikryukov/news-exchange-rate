package com.gd.amik.controllers.news.guardian;

import java.io.Serializable;
import java.util.List;

/**
 * Describe single piece of news according to guardianapis.com API.
 */
public class PieceOfNews implements Serializable {

    private final String topic;

    private final String title;

    private final String main;

    private final List<String> keywords;

    private final String date;

    private final String webURL;

    private final String thumbnail;

    /**
     * Used by JaxB
     */
    private PieceOfNews() {
        this.topic = null;
        this.title = null;
        this.main = null;
        this.keywords = null;
        this.date = null;
        this.webURL = null;
        this.thumbnail = null;
    }

    public PieceOfNews(String topic, String title, String main, List<String> keywords, String date, String webUrl,
                       String thumbnail) {
        this.topic = topic;
        this.title = title;
        this.main = main;
        this.keywords = keywords;
        this.date = date;
        this.webURL = webUrl;
        this.thumbnail = thumbnail;
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

    public String getDate() {
        return date;
    }

    public String getWebURL() {
        return webURL;
    }

    public String getThumbnail() {
        return thumbnail;
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
