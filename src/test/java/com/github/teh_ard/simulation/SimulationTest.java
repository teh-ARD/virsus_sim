package com.github.teh_ard.simulation;

import com.github.teh_ard.person.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

    private Simulation sim;

    @BeforeEach
    void setUp() {
        sim = new Simulation(1000);

    }

    @Test
    void testDefaultIterationCount() {
        sim.addPerson(1, DummyPerson.class);
        for (int i = 0; i<999; ++i) {
           sim.update();
        }

        assertFalse(sim.isDone());

        sim.update();
        assertTrue(sim.isDone());
    }

    @Test
    void setMaxIterationCount() {
        sim.setMaxIterationCount(5);
        sim.addPerson(1, DummyPerson.class);
        for (int i = 0; i<4; ++i) {
            sim.update();
        }

        assertFalse(sim.isDone());

        sim.update();
        assertTrue(sim.isDone());
    }

}