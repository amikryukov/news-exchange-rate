package com.gd.amik.dto;

import com.gd.amik.controllers.news.guardian.SetOfNews;

import java.io.Serializable;
import java.util.Map;

public class SearchResult implements Serializable {

    private SetOfNews setOfNews;
    private Map<String, Long> topicFacetValues;

    public SearchResult(SetOfNews setOfNews, Map<String, Long> topicFacetValues) {
        this.setOfNews = setOfNews;
        this.topicFacetValues = topicFacetValues;
    }

    public SetOfNews getSetOfNews() {
        return setOfNews;
    }

    public Map<String, Long> getTopicFacetValues() {
        return topicFacetValues;
    }
}
