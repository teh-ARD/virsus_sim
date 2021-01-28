package com.github.teh_ard.simulation.map;

import com.github.teh_ard.person.Person;
import com.github.teh_ard.utils.Vector2;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimMap {
    private final Rectangle rect;
    private final List<Person> people = new ArrayList<>();
    private final Map<Integer, Map<Integer, Rectangle>> sectors = new HashMap<>();

    /**
     * Tworzy nową mapę oraz dzieli ją na mniejsze sektory po których będą poruszać się osoby
     * @param mapSize Rozmiar mapy
     */
    public SimMap(int mapSize) {
        mapSize *= 10;
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

    /**
     * Dodaje osobę do mapy oraz przydziela jej sektory
     * @param person dana osoba
     * @param lethalityLevel poziom śmiertelności podany przez użytkownika
     * @param incubationValue czas inkubacji podany przez użytkownika
     */
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

        person.setPosition(new Vector2(
                personalSectors.get(0).getX() + Math.random() * 10,
                personalSectors.get(0).getY() + Math.random() * 10
        ));

        person.setSectors(personalSectors);
    }

    /**
     * Zwraca listę osób
     * @return lista osób
     */
    public List<Person> getPeople() {
        return people;
    }

    public Map<Integer, Map<Integer, Rectangle>> getSectors() {
        return sectors;
    }
}
