package com.github.teh_ard.simulation.map;

import com.github.teh_ard.simulation.DummyPerson;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


class SimMapTest {

    @Test
    void generalSectors(){
        Random rand = new Random();
        int pog = rand.nextInt(1000) + 10;
        SimMap map = new SimMap(pog);
        assertEquals(map.getSectors().size(), pog);
   }


    @Test
    void testPersonalSectors() {
        SimMap map = new SimMap(1000);
        DummyPerson p = new DummyPerson();
        map.addPerson(p, 5, 2);
        assertEquals(p.getSectors().size(), 7);
        assertEquals(p.getSectors().stream().distinct().count(), 7);
    }


}