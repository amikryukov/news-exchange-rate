package com.gd.amik.controllers.series.util;

import org.junit.Test;

import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TimeSerieTest {

    @Test
    public void testAdding() {
        TimeSerie ts = new TimeSerie("testSeries");
        ts.addPoint(new Point(1, 1));
        ts.addPoint(new Point(4, 6));
        ts.addPoint(new Point(5, 8));

        Set<Point> tsPoints = ts.getAllPoints();
        assertEquals(3, tsPoints.size());

        Point firstPoint = tsPoints.iterator().next();
        assertEquals(new Point(1, 1), firstPoint);
    }

    @Test
    public void testSubset() {
        TimeSerie ts = new TimeSerie("testSerie");
        for (int i = 0; i < 10; i ++) {
            ts.addPoint(new Point(i, i));
        }

        Set<Point> tsSubSet = ts.getSubSet(4, 6);
        assertEquals(2, tsSubSet.size());
        Iterator<Point> tsIter = tsSubSet.iterator();
        long tsTemp = 4;
        while(tsIter.hasNext()) {
            Point point = tsIter.next();
            assertEquals(new Point(tsTemp, tsTemp), point);
            tsTemp ++;
        }
    }

    @Test
    public void testSubsetWithUnexistedKeys() {
        TimeSerie ts = new TimeSerie("testSerie");
        for (int i = 1; i < 10; i += 2) { // [1, 3, 5, 7, 9]
            ts.addPoint(new Point(i, i));
        }

        Set<Point> tsSubSet = ts.getSubSet(2, 6);
        assertEquals(2, tsSubSet.size());
        Iterator<Point> tsIter = tsSubSet.iterator();
        assertEquals(new Point(3, 3), tsIter.next());
        assertEquals(new Point(5, 5), tsIter.next());
    }

    @Test
    public void testSubsetEmpty() {

        // empty subset
        TimeSerie ts = new TimeSerie("testSerie");
        for (int i = 1; i < 10; i += 2) { // [1, 3, 5, 7, 9]
            ts.addPoint(new Point(i, i));
        }

        Set<Point> tsSubSet = ts.getSubSet(2, 2);
        assertTrue(tsSubSet.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSubsetException() {

        // empty subset
        TimeSerie ts = new TimeSerie("testSerie");
        for (int i = 1; i < 10; i += 2) { // [1, 3, 5, 7, 9]
            ts.addPoint(new Point(i, i));
        }

        ts.getSubSet(4, 2);
    }
}
