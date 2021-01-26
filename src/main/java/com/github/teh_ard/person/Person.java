package com.github.teh_ard.person;

import com.github.teh_ard.simulation.map.SimMap;
import com.github.teh_ard.utils.MapPoint;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Person {
    private MapPoint velocity;
    private MapPoint position;
    private boolean died = false;
    private boolean infected = false;
    int incubationPeriod = -1;
    private int currentSector = 0;
    private List<Rectangle> sectors = null;
    private int bounce = 0;
    private boolean wanderingToCurrentSector = false;
    private Random rand = new Random();

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
        if (infected) {
            this.incubationPeriod = 2;
        }

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
        return isDead() || isInfected() && incubationPeriod == 0 && Math.random() > getDeathThreshold();
    }

    /**
     * Odpowiada za ruch osoby
     *
     *
     */
    public void move() {
        if (sectors == null) {
            return;
        }

        MapPoint oldVelocity = velocity;
        if (wanderingToCurrentSector && !sectors.get(currentSector).contains(position.getX(), position.getY())) {
            position.add(velocity);
            return;
        }

        if (wanderingToCurrentSector && sectors.get(currentSector).contains(position.getX(), position.getY())) {
            wanderingToCurrentSector = false;
        }

        while (!sectors.get(currentSector).contains(position.getX() + velocity.getX(), position.getY() + velocity.getY())) {
            velocity = new MapPoint(
                    (Math.random() * 2 - 1) * getMaxSpeed(),
                    (Math.random() * 2 - 1) * getMaxSpeed()
            );
        }

        if (!oldVelocity.equals(velocity) && bounce++ > 3) {
            bounce = 0;
            int oldSector = currentSector;
            while (oldSector == currentSector) {
                currentSector = rollSector();
            }

            velocity = new MapPoint(
                    sectors.get(currentSector).getX() + position.getX() + Math.random() * 10,
                    sectors.get(currentSector).getY() + position.getY() + Math.random() * 10
            );
            velocity.scale(1/Math.sqrt(velocity.distanceToSquared(position)));
            wanderingToCurrentSector = true;
        }

        position.add(velocity);
    }

    public int rollSector() {
        int value = 0;
        for (int i = 0; i < 4; ++i) {
            value += rand.nextInt(3);
        }
        return Math.max(0, -2 + value);
    }

    public void setSectors(List<Rectangle> sectors) {
        this.sectors = sectors;
    }



    /**
     * Zwraca pobliskie osoby
     * @param map Mapa symulacji
     * @return Pobliskie osoby
     */
    public List<Person> getNearby(SimMap map) {
        List<Person> result = new ArrayList<>();
        for (Person person : map.getPeople()) {
            if (person.equals(this)) {
                continue;
            }

            if (person.getPosition().distanceToSquared(this.getPosition()) <= 9){
                result.add(person);
            }
        }

        return result;
    }

    public void update(SimMap map) {
        if (incubationPeriod > 0) {
            incubationPeriod--;
        }

       for (Person person : getNearby(map)) {
           if (!person.isInfected() && canInfect(person)) {
               if (person.wasInfected()){
                   person.setInfected(Math.random() > 0.5);
                   continue;
               }

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

    public boolean wasInfected() {
        return !infected && incubationPeriod == 0;
    }
}
