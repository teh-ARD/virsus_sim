package com.github.teh_ard.person;

public class Elder extends Person {
    @Override
    public boolean canInfect(Person person) {
        return Math.random() > 0.4;
    }

    @Override
    public int getMaxSpeed() {
        return 1;
    }

    @Override
    protected double getDeathThreshold() {
        return 0.7;
    }


}
