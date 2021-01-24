package com.github.teh_ard.person;

import com.github.teh_ard.simulation.map.SimMap;

public class Doctor extends Adult {

    @Override
    public boolean canInfect(Person person) {
        return Math.random() > 0.9;
    }

    @Override
    protected double getDeathThreshold() {
        return 0.7;
    }

    @Override
    public boolean shouldDie() {
        return super.shouldDie();
    }

    @Override
    public void update(SimMap map) {
        if (incubationPeriod > 0) {
            incubationPeriod--;
        }
        for (Person person : getNearby(map)) {
            if (canInfect(person)) {
                person.setInfected(true);
            }

            if (person.incubationPeriod == 0 && Math.random() <= 0.9) {
                person.setInfected(false);
            }
        }
    }
}
