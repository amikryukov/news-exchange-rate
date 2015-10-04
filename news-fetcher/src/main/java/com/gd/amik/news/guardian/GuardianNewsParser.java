package com.gd.amik.news.guardian;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
            String main = theNew.getJsonObject("fields").containsKey("main") ?
                    theNew.getJsonObject("fields").getString("main") : null;
            if (main != null) {
                // get only text - no tags? etc ...
            }

            List<String> keywords = new ArrayList<String>();
            JsonArray jsonArray = theNew.getJsonArray("tags");
            for (int j = 0; j < jsonArray.size(); j ++) {
                keywords.add(jsonArray.getJsonObject(j).getString("webTitle"));
            }
//             // No need to create long
//            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
//            Date date = null;
//            try {
//                date = dateFormatter.parse(theNew.getString("webPublicationDate"));
//            } catch (ParseException e) {
//                // todo: add logger
//                e.printStackTrace();
//            }
            PieceOfNews result = new PieceOfNews(topic, title, main, keywords, theNew.getString("webPublicationDate"));
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
