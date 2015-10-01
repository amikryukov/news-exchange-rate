package com.gd.amik.series;

import com.gd.amik.series.util.Point;
import com.gd.amik.series.util.TimeSerie;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SeriesLoaderTest {

    @Test
    public void happyPath() throws IOException, ParseException {
        SeriesLoader sl = new SeriesLoader();

        TimeSerie timeSerie = sl.load("src/test/recources/test.series.data.csv");

        assertFalse(timeSerie.getAllPoints().isEmpty());
    }

    @Test
    public void allDataInMemoryTest() throws Exception {
        SeriesLoader sl = new SeriesLoader();

        TimeSerie timeSerie = sl.load("src/main/resources/DAT_NT_EURUSD_T_ASK_201508.csv"); // ~ 26mb
        TimeSerie timeSerie09 = sl.load("src/main/resources/DAT_NT_EURUSD_T_ASK_201509.csv"); // ~ 25mb

        timeSerie.addAllPoints(timeSerie09.getAllPoints());

        assertTrue(timeSerie.getAllPoints().size() > 1_000_000);
    }
}
