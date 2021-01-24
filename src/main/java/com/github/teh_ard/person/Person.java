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
    private boolean infected = false;

    public Person() {
        velocity = new MapPoint(
                (Math.random() * 2 - 1) * getMaxSpeed(),
                (Math.random() * 2 - 1) * getMaxSpeed()
        );
    }

    public abstract boolean canInfect(Person person);
    public abstract int getMaxSpeed();
    protected abstract double getDeathThreshold();

    public void setInfected(boolean infected) {
        this.infected = infected;
    }

    public boolean isInfected() {
        return infected;
    }

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
    public boolean shouldDie() {
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
    public List<Person> getNearby(SimMap map) {
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
        for (Person person : getNearby(map)) {
            if (canInfect(person)) {
                person.setInfected(true);
            }
        }
    }

    public void setPosition(MapPoint position) {
        this.position = position;
    }

    public MapPoint getPosition() {
        return position;
    }

    public void setDied(boolean died) {
        this.died = died;
    }
}
