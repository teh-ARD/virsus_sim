package com.github.teh_ard.simulation;

import com.github.teh_ard.simulation.map.SimMap;
import com.github.teh_ard.person.Person;

import java.util.ArrayList;
import java.util.List;

public class Simulation {

    private final SimMap map;
    List<Person> people = new ArrayList<>();

    public Simulation(int mapSize) {
        map = new SimMap(mapSize);
    }
    
    public void addPerson(int count, Class<? extends Person> clazz) {

        for (int i = 0; i < count; ++i) {
            try {
                Person person = clazz.newInstance();
                map.addPerson(person);
                people.add(person);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}
