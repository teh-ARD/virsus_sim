package com.github.teh_ard.simulation.map;

import com.github.teh_ard.person.Person;

import java.util.ArrayList;
import java.util.List;

public class SimMap {
    private final int size;
    private List<Person> people = new ArrayList<>();

    public SimMap(int mapSize) {
        size = mapSize;
    }

    public void addPerson(Person person) {
        people.add(person);
    }
}
