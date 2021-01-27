package com.github.teh_ard;

import com.github.teh_ard.person.*;
import com.github.teh_ard.simulation.Simulation;

import java.security.InvalidParameterException;
import java.util.InputMismatchException;
import java.util.Scanner;

// https://github.com/teh-ARD/virus_sim

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Pyta użytkownika o wartości na jakich symulacja ma działać, zaraża pierwsze osoby oraz odpala symulacje na
     * tych parametrach
     * @param args argumenty podane do programu
     */
    public static void main(String[] args) {

//      TODO: ew. zapytanie "czy chcesz zmieniać domyślne wartości?" żeby nie było tyle klikania enterem

        System.out.println("Podaj rozmiar mapy (domyślnie: 100)");
        Simulation sim = new Simulation(readInt(100));

        System.out.println("Podaj wskaźnik śmiertelności wirusa [1-10] (domyślnie: 5)");
        int lethalityLevel = Math.min(10, readInt(5));

        System.out.println("Podaj czas inkubacji wirusa (domyślnie: 2)");
        int incubationValue = readInt(2);

        System.out.println("Podaj liczbę lekarzy (domyślnie: 25)");
        sim.addPerson(readInt(25), lethalityLevel, incubationValue, Doctor.class);

        System.out.println("Podaj liczbę dorosłych (domyślnie: 250)");
        sim.addPerson(readInt(250), lethalityLevel, incubationValue, Adult.class);

        System.out.println("Podaj liczbę dzieci (domyślnie: 150)");
        sim.addPerson(readInt(150), lethalityLevel, incubationValue, Child.class);

        System.out.println("Podaj liczbę starców (domyślnie: 50)");
        sim.addPerson(readInt(50), lethalityLevel, incubationValue, Elder.class);

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
