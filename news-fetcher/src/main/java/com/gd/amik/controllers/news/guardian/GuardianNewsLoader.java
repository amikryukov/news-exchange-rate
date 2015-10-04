package com.gd.amik.controllers.news.guardian;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

public class GuardianNewsLoader {

    // key to be able to use guardian api
    private final String apiKey;

    private final HttpClient httpClient;

    private static final String API_HOST = "content.guardianapis.com";
    private static final String SEARCH_SCHEMA = "http";
    private static final String SEARCH_PATH = "/search";

    private final GuardianNewsParser newsParser;
//            "?api-key=test&show-fields=main,headline,standfirst&show-tags=keyword&page=94&page-size=200&order-by=oldest&from-date=2015-08-01&to-date" +
//            "=2015-09-29";

    public GuardianNewsLoader(String apiKey) {
        this.apiKey = apiKey;
        // use default http client
        this.httpClient = HttpClientBuilder.create().build();
        this.newsParser = new GuardianNewsParser();
    }

    public SetOfNews getSetOfNews(String fromDate, String toDate, int pageSize, int page) {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme(SEARCH_SCHEMA).setHost(API_HOST).setPath(SEARCH_PATH)
                .setParameter("api-key", apiKey)
                .setParameter("show-fields", "thumbnail")
                .setParameter("show-tags", "keyword")
                .setParameter("page-size", pageSize + "")
                .setParameter("page", page + "")
                .setParameter("from-date", fromDate)
                .setParameter("to-date", toDate);

        try {
            HttpGet request = new HttpGet(uriBuilder.build().toASCIIString());
            HttpResponse response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("code is not 200");
            }

            return newsParser.parse(response.getEntity().getContent());
        } catch (URISyntaxException e) {
            System.out.println("URISyntaxException: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (ClientProtocolException e) {
            System.out.println("ClientProtocolException: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public InputStream getInputStream(String fromDate, String toDate, int pageSize, int page) {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme(SEARCH_SCHEMA).setHost(API_HOST).setPath(SEARCH_PATH)
                .setParameter("api-key", apiKey)
                .setParameter("show-fields", "main,headline,standfirst")
                .setParameter("show-tags", "keyword")
                .setParameter("page-size", pageSize + "")
                .setParameter("page", page + "")
                .setParameter("from-date", fromDate)
                .setParameter("to-date", toDate);

        try {
            HttpGet request = new HttpGet(uriBuilder.build().toASCIIString());
            HttpResponse response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("code is not 200");
            }

            return response.getEntity().getContent();
        } catch (URISyntaxException e) {
            System.out.println("URISyntaxException: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (ClientProtocolException e) {
            System.out.println("ClientProtocolException: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
