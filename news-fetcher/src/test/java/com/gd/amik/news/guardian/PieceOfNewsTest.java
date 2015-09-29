package com.gd.amik.news.guardian;

import org.junit.Test;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PieceOfNewsTest {

    @Test
    public void simpleUnMarshalTest() throws Exception {

        JsonReader jReader = Json.createReader(new FileInputStream("src/test/java/recources/piece_of_news.json"));
        JsonObject jObject = jReader.readObject();

        String topic = jObject.getString("sectionName");
        String title = jObject.getString("webTitle");
        String main = jObject.getJsonObject("fields").getString("main");

        List<String> keywords = new ArrayList<String>();
        JsonArray jsonArray = jObject.getJsonArray("tags");
        for (int i = 0; i < jsonArray.size(); i ++) {
            keywords.add(jsonArray.getJsonObject(i).getString("webTitle"));
        }

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
        Date date = dateFormatter.parse(jObject.getString("webPublicationDate"));

        PieceOfNews result = new PieceOfNews(topic, title, main, keywords, date);

        assertEquals("Australia news", topic);
    }
}
