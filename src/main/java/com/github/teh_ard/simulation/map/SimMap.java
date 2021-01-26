package com.github.teh_ard.simulation.map;

import com.github.teh_ard.person.Person;
import com.github.teh_ard.utils.MapPoint;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimMap {
    private final Rectangle rect;
    private final List<Person> people = new ArrayList<>();
    private final Map<Integer, Map<Integer, Rectangle>> sectors = new HashMap<>();

    public SimMap(int mapSize) {
        rect = new Rectangle(0, 0, mapSize, mapSize);
        for (int y = 0; y < mapSize; y += 10) {
            Map<Integer, Rectangle> row = new HashMap<>();
            for (int x = 0; x < mapSize; x += 10){
                Rectangle rect2 = new Rectangle(x, y, 10, 10);
                row.put(x, rect2);
            }

            sectors.put(y, row);
        }
    }

    public void addPerson(Person person) {
        people.add(person);
        int y = (int) (Math.random() * (rect.height / 10)) * 10;
        int x = (int) (Math.random() * (rect.width / 10)) * 10;
        Rectangle homeRect = sectors.get(y).get(x);
        Rectangle workRect = homeRect;

        while (workRect == homeRect) {
            workRect = sectors.get((int) (Math.random() * (rect.height / 10)) * 10).get((int) (Math.random() * (rect.width / 10)) * 10);
        }

        person.setCurrentSector(homeRect);
        person.setPosition(new MapPoint(
                homeRect.getX() + Math.random() * 10,
                homeRect.getY() + Math.random() * 10
        ));

        person.setHome(homeRect);
        person.setWork(workRect);
    }

    public void update() {
    }

    public boolean contains(double v, double v1) {
        return rect.contains(v,v1);
    }

    public boolean contains(MapPoint point) {
        return contains(point.getX(), point.getY());
    }

    public List<Person> getPeople() {
        return people;
    }
}
