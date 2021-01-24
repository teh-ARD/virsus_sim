package com.github.teh_ard.person;

import com.github.teh_ard.simulation.map.SimMap;

public class Doctor extends Person {
    @Override
    public boolean canInfect(Person person) {
        return false;
    }

    @Override
    public boolean isInfected() {
        return false;
    }

    @Override
    public int getMaxSpeed() {
        return 1;
    }

    @Override
    protected double getDeathThreshold() {
        return 0;
    }

    @Override
    public void update(SimMap map) {
        if (shouldDie()) {
            setDied(true);
            return;
        }

        move(map);

        for (Person person : getNearby(map)) {
            if (canInfect(person)) {
                person.setInfected(true);
            }

            if (Math.random() <= 0.9) {
                person.setInfected(false);
            }
        }
    }
}
