package com.github.teh_ard;

import com.github.teh_ard.person.*;
import com.github.teh_ard.simulation.Simulation;

import java.security.InvalidParameterException;
import java.util.InputMismatchException;
import java.util.Scanner;

// https://github.com/teh-ARD/virus_sim

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Podaj rozmiar mapy (domyślnie: 100)");
        Simulation sim = new Simulation(readInt(100) * 10);

        System.out.println("Podaj liczbę lekarzy (domyślnie: 100)");
        sim.addPerson(readInt(100), Doctor.class);

        System.out.println("Podaj liczbę dorosłych (domyślnie: 250)");
        sim.addPerson(readInt(250), Adult.class);

        System.out.println("Podaj liczbę dzieci (domyślnie: 150)");
        sim.addPerson(readInt(150), Child.class);

        System.out.println("Podaj liczbę starców (domyślnie: 50)");
        sim.addPerson(readInt(50), Elder.class);

        System.out.println("Podaj liczbę zarażonych (domyślnie: 50)");
        int infected = readInt(50);

        System.out.println("Podaj maksymalną liczbę iteracji (domyślnie: 1000)");
        sim.setMaxIterationCount(readInt(1000));

        while (infected-- > 0) {
            Person person = sim.getPeople().get((int) (Math.random() * sim.getPeople().size()));
            if (person.isInfected()){
                infected++;
                continue;
            }
            person.setInfected(true);
        }

        sim.start();
    }

    /**
     * Sczytuje wprowadzoną wartość i zakańcza działanie programu w momencie gdy wystąpi błąd
     * @return nieujemna liczba całkowita
     */
    private static int readInt(int def) {
        try {
            String line = scanner.nextLine();
            if (line.matches("-?\\d+(\\.\\d+)?")) {
                int res = Integer.parseInt(line);
                if (res >= 0) {
                    return res;
                }
                System.out.println("Liczba nie może być ujemna! Ustawiono domyślną wartość");
                return def;
            }

            if (line.matches("^$")) {
                return def;
            }

            System.out.println("Następnym razem podaj poprawną liczbę! Ustawiono domyślną wartość");
            return def;

        } catch (InvalidParameterException | InputMismatchException | NumberFormatException e) {
            System.out.println("Następnym razem podaj poprawną liczbę! Ustawiono domyślną wartość");
            return def;
        }
    }
}
