package com.github.teh_ard.person;

import com.github.teh_ard.simulation.DummyPerson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    void rollSector() {
        Person p = new DummyPerson();

        boolean had0 = false;
        boolean had6 = false;

        for (int i = 0; i < 10000; ++i) {
            int roll = p.rollSector();
            assertTrue(roll >= 0 && roll <= 6);

            if (roll == 0) { had0 = true; }
            if (roll == 6) { had6 = true; }
        }

        assertTrue(had0);
        assertTrue(had6);
    }
}