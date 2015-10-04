package com.gd.amik.controllers.news.guardian;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GuardianNewsParser {
    public SetOfNews parse(InputStream content) {
        JsonReader jsonReader = Json.createReader(content);
        JsonObject jsonObject = jsonReader.readObject().getJsonObject("response");
        SetOfNews setOfNews =
                new SetOfNews(jsonObject.getInt("pages"), jsonObject.getInt("currentPage"), jsonObject.getInt("total"));

        JsonArray newsArray = jsonObject.getJsonArray("results");

        for (int i = 0; i < newsArray.size(); i ++) {
            JsonObject theNew = newsArray.getJsonObject(i);

            String topic = theNew.getString("sectionName");
            String title = theNew.getString("webTitle");
            JsonObject fields = theNew.getJsonObject("fields");

            String thumbnail = null;
            if (fields != null) {
                thumbnail = fields.containsKey("thumbnail") ?
                        theNew.getJsonObject("fields").getString("thumbnail") : null;
            }

            String webUrl = theNew.getString("webUrl");


            List<String> keywords = new ArrayList<>();
            JsonArray jsonArray = theNew.getJsonArray("tags");
            for (int j = 0; j < jsonArray.size(); j ++) {
                keywords.add(jsonArray.getJsonObject(j).getString("webTitle"));
            }
            PieceOfNews result =
                    new PieceOfNews(
                            topic,
                            title,
                            null,
                            keywords,
                            theNew.getString("webPublicationDate"),
                            webUrl,
                            thumbnail);
            setOfNews.addNew(result);
        }

        try {
            content.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return setOfNews;
    }
}
