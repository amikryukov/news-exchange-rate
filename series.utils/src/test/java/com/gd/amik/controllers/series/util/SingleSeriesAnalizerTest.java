package com.gd.amik.controllers.series.util;

import com.gd.amik.controllers.series.SeriesLoader;
import com.gd.amik.controllers.series.SingleSeriesAnalyzer;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SingleSeriesAnalizerTest {


    @Test
    public void happyPath() throws Exception {

        SeriesLoader sl = new SeriesLoader();
        TimeSerie timeSerie = sl.load("src/test/recources/test.series.data.csv");

        SingleSeriesAnalyzer analizer = new SingleSeriesAnalyzer("test", timeSerie);
        double activity = analizer.calculateActivity(1438521540000L, 1438521555000L);
        assertTrue(activity > 0);

        activity = analizer.calculateActivity(1438520554000L, 1438520589000L);//1438522498000L);
        assertTrue(activity < 0);
    }
}
