package com.github.teh_ard.person;

public abstract class Person {

    abstract boolean canInfect(Person person);
    abstract void heal(Person person);
    abstract void infect(Person person);

    private boolean isDead() {
        return false;
    }

    private boolean shouldDie() {
        return false;
    }

    public void move() {

    }

    private void checkNearby() {

    }

}
