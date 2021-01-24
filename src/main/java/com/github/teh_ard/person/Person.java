package com.github.teh_ard.person;

import com.github.teh_ard.simulation.map.SimMap;
import com.github.teh_ard.utils.MapPoint;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public abstract class Person {

    private MapPoint velocity;
    private MapPoint position;
    private boolean died = false;

    public Person() {
        velocity = new MapPoint(
                (Math.random() * 2 - 1) * getMaxSpeed(),
                (Math.random() * 2 - 1) * getMaxSpeed()
        );
    }

    public abstract boolean canInfect(Person person);
    public abstract boolean isInfected();
    public abstract void heal(Person person);
    public abstract void infect(Person person);
    public abstract int getMaxSpeed();
    protected abstract double getDeathThreshold();

    /**
     * Zwraca czy osoba jest martwa czy żywa
     * @return Stan życia osoby
     */
    public boolean isDead() {
        return died;
    }

    /**
     * Sprawdza czy ustawia czy osoba powinna umrzeć
     * @return Stan życia osoby
     */
    private boolean shouldDie() {
        return isDead() || isInfected() && Math.random() > getDeathThreshold();
    }

    /**
     * Odpowiada za ruch osoby
     * @param map Mapa symulacji
     */
    public void move(SimMap map) {
        while (!map.contains(position.getX() + velocity.getX(), position.getY() + velocity.getY())) {
            velocity = new MapPoint(
                    (Math.random() * 2 - 1) * getMaxSpeed(),
                    (Math.random() * 2 - 1) * getMaxSpeed()
            );
        }
        position.add(velocity);
    }

    /**
     * Zwraca pobliskie osoby
     * @param map Mapa symulacji
     * @return Pobliskie osoby
     */
    private List<Person> getNearby(SimMap map) {
        List<Person> result = new ArrayList<>();
        for (Person person : map.getPeople()) {
            if (person == this) {
                continue;
            }

            if (person.getPosition().distanceToSquared(this.getPosition()) <= 9){
                result.add(person);
            }
        }

        return result;
    }

    public void update(SimMap map) {
        if (shouldDie()) {
            died = true;
            return;
        }

        move(map);

        for (Person person : getNearby(map)) {
            if (canInfect(person)) {
                infect(person);
            }
        }
    }

    public void setPosition(MapPoint position) {
        this.position = position;
    }

    public MapPoint getPosition() {
        return position;
    }
}
