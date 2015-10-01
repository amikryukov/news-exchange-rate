package com.gd.amik.series;

import com.gd.amik.series.util.Point;
import com.gd.amik.series.util.TimeSerie;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import java.util.Set;

/**
 * Analyze series data.
 */
public class SingleSeriesAnalyzer {

    private final String name;

    /**
     * Series to analyze.
     *
     * As project is POC, simple in memory data is used.
     * In real project source of the series data can be search engine (lucene) to deal with storage place and seeking
     * functionality.
     */
    private TimeSerie serie;

    /**
     * Constructs the analyzer.
     *
     * @param name name of analizer.
     * @param serie data to be analized.
     */
    public SingleSeriesAnalyzer(String name, TimeSerie serie) {
        this.name = name;
        this.serie = serie;
    }

    /**
     * Calculates activity of the series in specified interval [tStart, tEnd].
     *
     * @param tStart left bound of the interval.
     * @param tEnd right bound od the interval.
     *
     * @return measure of how active data was in interval.
     *  If measure > 0, then series has rising slope.
     *  If measure < 0, then series has decline slope.
     *  More abs value measure has - more active series is.
     */
    public double calculateActivity(long tStart, long tEnd) {
        Set<Point> subSet = serie.getSubSet(tStart, tEnd);
        return analiseSet(subSet);
    }

    /**
     * Now it is simple value of slope of liner approximation.
     * Todo : Add elements of fuzzy theory to detect anomalies on wider
     *
     * @param set set of points
     *
     * @return measure of how active data was in set.
     *  If measure > 0, then series has rising slope.
     *  If measure < 0, then series has decline slope.
     *  More abs value measure has - more active series is.
     */
    private double analiseSet(Set<Point> set) {

        if (set.size() < 2) {
            return 0D;
        }

        SimpleRegression regression = new SimpleRegression();

        for (Point point : set) {
            regression.addData(point.getTimestamp(), point.getValue());
        }

        return regression.getSlope(); // slope of the regression.
    }

    @Override
    public String toString() {
        return "SingleSeriesAnalyzer{" +
                "name='" + name + '\'' +
                ", serie=" + serie +
                '}';
    }
}
