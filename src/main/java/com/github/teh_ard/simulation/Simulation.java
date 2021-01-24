package com.github.teh_ard.simulation;

import com.github.teh_ard.simulation.map.SimMap;
import com.github.teh_ard.person.Person;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Simulation {
    private final Map<Class<? extends Person>, Integer> starterGroupCount = new HashMap<>();
    private final List<Person> people = new ArrayList<>();
    private int maxIterationCount = 1000;
    private int iterationCount = 1;
    private boolean done = false;

    private final SimMap map;
    private FileWriter writer;

    public Simulation(int mapSize) {
        map = new SimMap(mapSize);
    }

    public void start() {
        File file = new File("virus_sim - " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()) + ".csv");
        try {
            writer = new FileWriter(file);

            while (!done) {
                update();
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Dodaje osoby do mapy
     * @param count liczba osób do dodania
     * @param clazz klasa (rodzaj osoby) której instancja ma być dodana
     */
    public void addPerson(int count, Class<? extends Person> clazz) {
        if (!starterGroupCount.containsKey(clazz)) {
            starterGroupCount.put(clazz, 0);
        }

        starterGroupCount.put(clazz, starterGroupCount.get(clazz) + count);

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
        System.out.printf("==================== iteracja nr %d ====================%n", iterationCount);
        map.update();

        boolean anyoneAlive = false;
        boolean anyoneInfected = false;
        for (Person person : people) {
            if (person.shouldDie()) {
                person.setDied(true);
                continue;
            }

            person.move(map);
            person.update(map);

            if (!person.isDead()) {
                anyoneAlive = true;
            }

            if (person.isInfected()){
                anyoneInfected = true;
            }
        }

        int infected = 0;
        int dead = 0;

        Map<Class<? extends Person>, Integer> groupCount = new HashMap<>();
        for (Person person : people) {

            if (!groupCount.containsKey(person.getClass())) {
                groupCount.put(person.getClass(), 0);
            }

            if (person.isDead()) {
                dead++;
                continue;
            }

            if (person.isInfected()) {
                infected++;
            }

            groupCount.put(person.getClass(), groupCount.get(person.getClass()) + 1);
        }

        for(Map.Entry<Class<? extends Person>, Integer> entry : groupCount.entrySet()) {
            System.out.printf("%s: %d / %d %n", entry.getKey().getSimpleName(), entry.getValue(), starterGroupCount.get(entry.getKey()));
        }

        int alive = people.size() - dead;
        System.out.printf("Żywi: %d/%d (%.2f%%)%n", alive, people.size(), (double) alive / people.size() * 100);
        System.out.printf("Martwi: %d/%d (%.2f%%)%n", dead, people.size(), (double) dead / people.size() * 100);
        System.out.printf("Zdrowi: %d/%d (%.2f%%)%n", alive - infected, alive, (double) (alive - infected) / alive * 100);
        System.out.printf("Zarażeni: %d/%d (%.2f%%)%n", infected, alive, (double) infected / alive * 100);

        if (!anyoneAlive || !anyoneInfected || iterationCount++ >= maxIterationCount ) {
            done = true;
        }

        if (writer != null) {
            try {
                writer.write(String.format("Iteracja %d, ",iterationCount - 1));

                for(Map.Entry<Class<? extends Person>, Integer> entry : groupCount.entrySet()) {
                    writer.write(String.format("%s: %d / %d,", entry.getKey().getSimpleName(), entry.getValue(), starterGroupCount.get(entry.getKey())));
                }

                writer.write(String.format("\"Żywi: %d/%d (%.2f%%)\",", alive, people.size(), (double) alive / people.size() * 100));
                writer.write(String.format("\"Martwi: %d/%d (%.2f%%)\",", dead, people.size(), (double) dead / people.size() * 100));
                writer.write(String.format("\"Zdrowi: %d/%d (%.2f%%)\",", alive - infected, alive, (double) (alive - infected) / alive * 100));
                writer.write(String.format("\"Zarażeni: %d/%d (%.2f%%)\"%n", infected, alive, (double) infected / alive * 100));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Zwraca informacje o zakończeniu symulacji
     * @return Czy symulacja zakończona
     */
    public boolean isDone() {
        return done;
    }

    /**
     * Zwraca listę osób w symulacji
     * @return Lista osób w symulacji
     */
    public List<Person> getPeople() {
        return people;
    }

    /**
     * Zwraca mapę symulacji
     * @return Mapa symulacji
     */
    public SimMap getMap() {
        return map;
    }
}
