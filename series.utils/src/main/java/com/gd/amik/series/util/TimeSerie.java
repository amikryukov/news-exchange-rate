package com.gd.amik.series.util;

import java.util.*;

/**
 * Represents time serie
 */
public class TimeSerie {

    private final String name;

    private TreeSet<Point> points = new TreeSet<>(new Comparator<Point>() {
        @Override
        public int compare(Point o1, Point o2) {
            return o1.getTimestamp() > o2.getTimestamp() ? 1 : -1;
        }
    });

    public TimeSerie(String name) {
        this.name = name;
    }

    public void addPoint(final Point point) {
        points.add(point);
    }

    public void addAllPoints(final Collection<Point> pointsToAdd) {
        points.addAll(pointsToAdd);
    }

    public Set<Point> getAllPoints() {
        return points;
    }

    public Set<Point> getSubSet(long tStart, long tStop) {
        Point fakeStartPoint = new Point(tStart, 0);
        Point fakeEndPoint = new Point(tStop, 0);
        return points.subSet(fakeStartPoint, fakeEndPoint);
    }

    @Override
    public String toString() {
        return "TimeSerie{" +
                "name='" + name + '\'' +
                "count='" + points.size() + '\'' +
                '}';
    }
}
