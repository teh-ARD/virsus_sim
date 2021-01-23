package com.github.teh_ard.simulation.map;

import com.github.teh_ard.person.Person;
import com.github.teh_ard.utils.MapPoint;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SimMap {
    private final Rectangle rect;
    private List<Person> people = new ArrayList<>();

    public SimMap(int mapSize) {
        rect = new Rectangle(0,0,mapSize,mapSize);

    }

    public void addPerson(Person person) {
        people.add(person);
        person.setPosition(new MapPoint(
                Math.random() * rect.width,
                Math.random() * rect.height
        ));
    }

    public void update() {
    }

    public boolean contains(double v, double v1) {
        return rect.contains(v,v1);
    }
}
