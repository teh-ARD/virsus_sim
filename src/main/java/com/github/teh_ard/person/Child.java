package com.github.teh_ard.person;

public class Child extends Person {
    @Override
    public boolean canInfect(Person person) {
        return Math.random() > 0.5;
    }

    @Override
    public int getMaxSpeed() {
        return 2;
    }

    @Override
    protected double getDeathThreshold() {
        return 0.95;
    }
}
