package com.github.teh_ard.utils;

/**
 * Klasa odpowiada za tworzenie wektorów
 */
public class Vector2 {
    double x;
    double y;

    /**
     * Tworzy wektor
     * @param x współrzędna x
     * @param y współrzędna y
     */
    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Zwraca współrzędną x
     * @return współrzędna x
     */
    public double getX() {
        return x;
    }

    /**
     * Zwraca współrzędną y
     * @return współrzędna y
     */
    public double getY() {
        return y;
    }

    /**
     * Dodaje do siebie wektory
     * @param p2 wektor do dodania
     */
    public void add(Vector2 p2) {
        x += p2.getX();
        y += p2.getY();
    }

    /**
     * Oblicza kwadrat dystansu do innego wektora
     * @param p2 drugi wektor
     * @return kwadrat dystansu
     */
    public double distanceToSquared(Vector2 p2) {
        return Math.pow(getX() - p2.getX(), 2) + Math.pow(getY() - p2.getY(), 2);
    }

    /**
     * Zamienia wektor na string
     * @return string z współrzędnymi wektora
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ')';
    }

    /**
     * Skaluje wektor
     * @param v skala wektora
     */
    public void scale(double v) {
        x *= v;
        y *= v;
    }
}
