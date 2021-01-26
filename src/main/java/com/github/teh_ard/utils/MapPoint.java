package com.github.teh_ard.utils;

public class MapPoint {
    double x;
    double y;

    public MapPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void add(MapPoint p2) {
        x += p2.getX();
        y += p2.getY();
    }

    public double distanceToSquared(MapPoint p2) {
        return Math.pow(getX() - p2.getX(), 2) + Math.pow(getY() - p2.getY(), 2);
    }

    @Override
    public String toString() {
        return "(" +
                "" + x +
                ", " + y +
                ')';
    }

    public void scale(double v) {
        x *= v;
        y *= v;
    }
}
