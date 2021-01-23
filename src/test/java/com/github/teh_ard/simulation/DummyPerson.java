package com.github.teh_ard.simulation;

import com.github.teh_ard.person.Person;

public class DummyPerson extends Person {
    @Override
    public boolean canInfect(Person person) {
        return false;
    }

    @Override
    public boolean isInfected() {
        return true;
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
