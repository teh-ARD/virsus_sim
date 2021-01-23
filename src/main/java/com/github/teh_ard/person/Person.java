package com.github.teh_ard.person;

import com.github.teh_ard.simulation.map.SimMap;
import com.github.teh_ard.utils.MapPoint;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;
import java.util.Vector;

public abstract class Person {

    private MapPoint velocity;
    private MapPoint position;

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

    public boolean isDead() {
        return false;
    }

    private boolean shouldDie() {
        return false;
    }

    public void move() {

    }

    private void checkNearby() {

    }

    public void update(SimMap map) {
        while (!map.contains(position.getX() + velocity.getX(), position.getY() + velocity.getY())) {
            velocity = new MapPoint(
                    (Math.random() * 2 - 1) * getMaxSpeed(),
                    (Math.random() * 2 - 1) * getMaxSpeed()
            );
        }
        position.add(velocity);
    }

    public void setPosition(MapPoint position) {
        this.position = position;
    }
}
