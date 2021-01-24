package com.github.teh_ard.person;

public class Adult extends Person {
    @Override
    public boolean canInfect(Person person) {
        return Math.random() > 0.6;
    }

    @Override
    public int getMaxSpeed() {
        return 5;
    }

    @Override
    protected double getDeathThreshold() {
        return 0.85;
    }
}
