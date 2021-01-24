package com.github.teh_ard.person;

import com.github.teh_ard.simulation.map.SimMap;

public class Doctor extends Adult {

    @Override
    public boolean canInfect(Person person) {
        return Math.random() > 0.9;
    }

    @Override
    protected double getDeathThreshold() {
        return 0.9;
    }

    @Override
    public void update(SimMap map) {
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
