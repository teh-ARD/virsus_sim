package com.github.teh_ard.person;

public class Doctor extends Person {
    @Override
    boolean canInfect(Person person) {
        return false;
    }

    @Override
    void heal(Person person) {

    }

    @Override
    void infect(Person person) {

    }
}
