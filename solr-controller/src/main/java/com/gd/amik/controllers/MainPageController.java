package com.gd.amik.controllers;

import com.gd.amik.SearchService;
import com.gd.amik.dto.SearchResult;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
public class MainPageController {

    private static final Logger log = LoggerFactory.getLogger(MainPageController.class);

    @Autowired
    private SearchService searchService;

    @Autowired
    private SimpleDateFormat dateFormat;

    @RequestMapping("/")
    public ModelAndView load(
            @RequestParam(value = "startDate", required = false, defaultValue = "2015-08-01T00:00:00Z")
            String startDate,
            @RequestParam(value = "endDate", required = false, defaultValue = "2015-12-01T00:00:00Z")
            String endDate,
            @RequestParam(value = "topic", required = false) String topic
    ) throws IOException, SolrServerException, ParseException {

        long startTime = dateFormat.parse(startDate).getTime();
        long endTime = dateFormat.parse(endDate).getTime();

        long t = System.currentTimeMillis();
        SearchResult searchResult = (topic == null || topic.isEmpty()) ?
                searchService.searchForDates(startTime, endTime) :
                searchService.searchTopicForDates(startTime, endTime, topic);
        t = System.currentTimeMillis() - t;

        ModelAndView mv = new ModelAndView("main_page");
        mv.addObject("startDate", startDate);
        mv.addObject("endDate", endDate);
        mv.addObject("timeElp", t);
        mv.addObject("topic", topic);
        mv.addObject("searchResult", searchResult);
        return mv;
    }
}
