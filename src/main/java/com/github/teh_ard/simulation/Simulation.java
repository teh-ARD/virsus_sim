package com.github.teh_ard.simulation;

import com.github.teh_ard.simulation.map.SimMap;
import com.github.teh_ard.person.Person;

import java.util.ArrayList;
import java.util.List;

public class Simulation {

    private final SimMap map;
    List<Person> people = new ArrayList<>();
    private boolean done = false;
    private int maxIterationCount = 1000;
    private int iterationCount = 1;


    public Simulation(int mapSize) {
        map = new SimMap(mapSize);
    }

    public void start() {
        while (!done) {
            update();
        }
    }

    /**
     * Dodaje osoby do mapy
     * @param count liczba osób do dodania
     * @param clazz klasa (rodzaj osoby) której instancja ma być dodana
     */
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

    /**
     * Ustala maksymalną długość trwania symulacji
     * @param maxIterationCount maksymalna liczba iteracji
     */
    public void setMaxIterationCount(int maxIterationCount) {
        this.maxIterationCount = maxIterationCount;
    }

    /**
     * Aktualizuje informacje o działaniu symulacji wraz z mapą i wszystkimi osobami
     */
    public void update() {
        map.update();

        boolean anyoneAlive = false;
        boolean anyoneInfected = false;
        for (Person person : people) {
            person.update();

            if (!person.isDead()) {
                anyoneAlive = true;
            }

            if (person.isInfected()){
                anyoneInfected = true;
            }
        }

        if (!anyoneAlive || !anyoneInfected || iterationCount++ >= maxIterationCount ) {
            done = true;
        }
    }

    /**
     * Zwraca informacje o zakończeniu symulacji
     * @return Czy symulacja zakończona
     */
    public boolean isDone() {
        return done;
    }
}
