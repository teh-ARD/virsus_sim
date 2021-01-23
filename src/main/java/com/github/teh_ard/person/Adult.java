package com.github.teh_ard.person;

public class Adult extends Person {
    @Override
    boolean canInfect(Person person) {
        return false;
    }

    @Override
    public boolean isInfected() {
        return false;
    }

    @Override
    void heal(Person person) {

    }

    @Override
    void infect(Person person) {

    }
}
