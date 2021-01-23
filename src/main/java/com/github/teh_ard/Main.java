package com.github.teh_ard;

import com.github.teh_ard.person.Adult;
import com.github.teh_ard.person.Child;
import com.github.teh_ard.person.Doctor;
import com.github.teh_ard.person.Elder;
import com.github.teh_ard.simulation.Simulation;

import java.security.InvalidParameterException;
import java.util.InputMismatchException;
import java.util.Scanner;

// https://github.com/teh-ARD/virus_sim

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Podaj rozmiar mapy");
        Simulation sim = new Simulation(readInt());

        System.out.println("Podaj liczbę lekarzy");
        sim.addPerson(readInt(), Doctor.class);

        System.out.println("Podaj liczbę dorosłych");
        sim.addPerson(readInt(), Adult.class);

        System.out.println("Podaj liczbę dzieci");
        sim.addPerson(readInt(), Child.class);

        System.out.println("Podaj liczbę starców");
        sim.addPerson(readInt(), Elder.class);

//      System.out.println("Podaj maksymalną liczbę iteracji");
//        sim.setMaxIterationCount(readInt());

    }

    /**
     * Sczytuje wprowadzoną wartość i zakańcza działanie programu w momencie gdy wystąpi błąd
     * @return nieujemna liczba
     */
    private static int readInt() {
        try {
            int res = scanner.nextInt();
            if (res < 0) {
                throw new InvalidParameterException();
            }

            return res;
        } catch (InvalidParameterException | InputMismatchException e) {
            System.out.println("Następnym razem podaj poprawną liczbę");
            System.exit(1);
            return 0;
        }
    }
}
