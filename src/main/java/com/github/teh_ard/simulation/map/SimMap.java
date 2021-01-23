package com.github.teh_ard.simulation.map;

import com.github.teh_ard.person.Person;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SimMap {
    private final Rectangle rect;
    private List<Person> people = new ArrayList<>();

    public SimMap(int mapSize) {
        rect = new Rectangle(0,0,mapSize,mapSize);

    }

    public void addPerson(Person person) {
        people.add(person);
    }

    public void update() {
    }
}
