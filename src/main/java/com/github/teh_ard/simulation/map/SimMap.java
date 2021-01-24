package com.github.teh_ard.simulation.map;

import com.github.teh_ard.person.Person;
import com.github.teh_ard.utils.MapPoint;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SimMap {
    private final Rectangle rect;
    private final List<Person> people = new ArrayList<>();

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

    public boolean contains(MapPoint point) {
        return contains(point.getX(), point.getY());
    }

    public List<Person> getPeople() {
        return people;
    }
}
