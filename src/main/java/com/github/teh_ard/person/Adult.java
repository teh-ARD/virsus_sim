package com.github.teh_ard.person;

public class Adult extends Person {
    @Override
    public boolean canInfect(Person person) {
        return false;
    }

    @Override
    public boolean isInfected() {
        return false;
    }

    @Override
    public void heal(Person person) {

    }

    @Override
    public void infect(Person person) {

    }

    @Override
    public int getMaxSpeed() {
        return 1;
    }
}
