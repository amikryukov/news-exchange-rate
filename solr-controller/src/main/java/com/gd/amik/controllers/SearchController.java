package com.gd.amik.controllers;

import com.gd.amik.controllers.news.guardian.PieceOfNews;
import com.gd.amik.controllers.news.guardian.SetOfNews;
import com.gd.amik.dto.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 */
@Controller
public class SearchController {

    private static final Logger log = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private HttpSolrClient httpSolrClient;

    @Autowired
    private SimpleDateFormat dateFormat;

    @RequestMapping("/searchForDates")
    @ResponseBody
    public SearchResult foo(
            @RequestParam(value = "start", required = true) long startTime,
            @RequestParam(value = "end", required = true) long endTime) throws IOException, SolrServerException {

        SolrQuery query = createBaseQuery(startTime, endTime);
        query.addFacetField("topic");

        log.info("Searching with query=" + query.toString());

        QueryResponse response = httpSolrClient.query(query);
        return convertSolrQueryResponse(response);
    }

    @RequestMapping("/searchTopicForDates")
    @ResponseBody
    public SearchResult foo(
            @RequestParam(value = "start", required = true) long startTime,
            @RequestParam(value = "end", required = true) long endTime,
            @RequestParam(value = "topic", required = true) String topic) throws IOException, SolrServerException {

        SolrQuery query = createBaseQuery(startTime, endTime);
        query.setFilterQueries("topic:\"" + topic + '\"');

        log.info("Searching with query=" + query.toString());

        QueryResponse response = httpSolrClient.query(query);
        return convertSolrQueryResponse(response);
    }

    private SearchResult convertSolrQueryResponse(QueryResponse queryResponse) {

        SolrDocumentList docList = queryResponse.getResults();
        SetOfNews setOfNews = new SetOfNews(
                (int) docList.getNumFound() / 10,
                (int) docList.getStart(),
                (int) docList.getNumFound());

        ListIterator<SolrDocument> docs = docList.listIterator();
        while (docs.hasNext()) {
            SolrDocument doc = docs.next();
            setOfNews.addNew(
                    new PieceOfNews(
                         String.valueOf(doc.get("topic")),
                            String.valueOf(doc.get("title")),
                            null,
                            null,
                            String.valueOf(doc.get("date")),
                            String.valueOf(doc.get("web_url")),
                            String.valueOf(doc.get("thumbnail"))
                    )
            );
        }

        Map<String, Long> topicFacetValues = new HashMap<>();
        FacetField topicFacet = queryResponse.getFacetField("topic");
        if (topicFacet != null) {
            for (FacetField.Count count : topicFacet.getValues()) {
                if (count.getCount() > 0) {
                    topicFacetValues.put(count.getName(), count.getCount());
                }
            }
        }

        return new SearchResult(setOfNews, topicFacetValues);
    }

    private SolrQuery createBaseQuery(long startTime, long endTime) {

        String startDate = dateFormat.format(new Date(startTime));
        String endDate = dateFormat.format(new Date(endTime));

        String q = "date:[" + startDate + " TO " + endDate + "]";
        String sortField = "eur_us_weight";
        int start = 0;
        int page = 10;

        SolrQuery query = new SolrQuery(q);
        query.addSort(sortField, SolrQuery.ORDER.desc);
        query.setStart(start);
        query.setRows(page);
        return query;
    }
}
