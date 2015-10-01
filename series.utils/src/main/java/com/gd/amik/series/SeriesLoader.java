package com.gd.amik.series;

import com.gd.amik.series.util.Point;
import com.gd.amik.series.util.TimeSerie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * As poc do not think of format.
 */
public class SeriesLoader {

    //20150802 170215
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd hhmmss");

    public TimeSerie load(String filePath) throws IOException, ParseException {

        File file = new File(filePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            TimeSerie result = new TimeSerie(file.getName());
            while (reader.ready()) {
                String line = reader.readLine();
                String[] splits = line.split(";");
                long timeStamp = dateFormat.parse(splits[0]).getTime();
                double value = Double.parseDouble(splits[1]);
                result.addPoint(new Point(timeStamp, value));
            }
            return result;
        }
    }
}
