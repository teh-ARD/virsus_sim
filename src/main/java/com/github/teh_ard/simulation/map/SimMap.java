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

    public void addPerson(Person person, double lethalityLevel, int incubationValue) {
        people.add(person);
        person.setLethalityLevel(lethalityLevel);
        person.setIncubationValue(incubationValue);
        List<Rectangle> personalSectors = new ArrayList<>();
        for (int i = 0; i < 7; ++i) {
            int y = (int) (Math.random() * (rect.height / 10)) * 10;
            int x = (int) (Math.random() * (rect.width / 10)) * 10;
            Rectangle sector = sectors.get(y).get(x);
            if (!personalSectors.contains(sector)) {
                personalSectors.add(sector);
            } else {
                i--;
            }
        }

        person.setPosition(new MapPoint(
                personalSectors.get(0).getX() + Math.random() * 10,
                personalSectors.get(0).getY() + Math.random() * 10
        ));

        person.setSectors(personalSectors);
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
