package com.github.teh_ard.person;

import com.github.teh_ard.simulation.map.SimMap;
import com.github.teh_ard.utils.Vector2;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Person {
    private Vector2 velocity;
    private Vector2 position;
    private boolean died = false;
    private boolean infected = false;
    int incubationPeriod = -1;
    private int currentSector = 0;
    private List<Rectangle> sectors = null;
    private int bounce = 0;
    private boolean wanderingToCurrentSector = false;
    private final Random rand = new Random();
    private double lethalityLevel = 10;
    private int incubationValue;

    /**
     * Tworzy nową osobę oraz ustanawia jej nowy wektor prędkości
     */
    public Person() {
        velocity = new Vector2(
                (Math.random() * 2 - 1) * getMaxSpeed(),
                (Math.random() * 2 - 1) * getMaxSpeed()
        );
    }

    public abstract boolean canInfect(Person person);
    public abstract int getMaxSpeed();
    protected abstract double getDeathThreshold();

    /**
     * Zmienia stan zarażenia osoby oraz resetuje czas inkubacji
     * @param infected nowy stan zarażenia
     */
    public void setInfected(boolean infected) {
        this.infected = infected;
        if (infected) {
            this.incubationPeriod = incubationValue;
        }

    }

    /**
     * Zwraca informację czy dana osoba jest zarażona
     * @return stan zarażenia osoby
     */
    public boolean isInfected() {
        return infected;
    }

    /**
     * Zwraca czy osoba jest martwa czy żywa
     * @return Stan życia osoby
     */
    public boolean isDead() {
        return died;
    }

    /**
     * Sprawdza czy ustawia czy osoba powinna umrzeć
     * @return Stan życia osoby
     */
    public boolean shouldDie() {
        return isDead()
                || isInfected()
                && incubationPeriod == 0
                && Math.random() > Math.min(0.99, (getDeathThreshold() + (-0.05 * lethalityLevel) + 0.25));
    }

    /**
     * Odpowiada za ruch osoby
     *
     * Krok 1: Sprawdza czy osoba podróżuje do innego sektora
     * Krok 2.1: Jeśli nie, dokonuje ruchu z ustaloną prędkością (wektorem)
     * Krok 2.2: Jeśli tak, ustawia nową prędkość poruszania się do sektora docelowego:
     * Krok 3: Jeśli osoba "wyszłaby" spoza sektora ponad 3 razy, losowany jest nowy sektor docelowy z przypisanych
     * danej osobie sektorów
     * Krok 4: Tworzony jest wektor w kierunku sektora docelowego
     */
    public void move() {
        if (sectors == null) {
            return;
        }

        Vector2 oldVelocity = velocity;
        if (wanderingToCurrentSector && !sectors.get(currentSector).contains(position.getX(), position.getY())) {
            position.add(velocity);
            return;
        }

        if (wanderingToCurrentSector && sectors.get(currentSector).contains(position.getX(), position.getY())) {
            wanderingToCurrentSector = false;
        }

        while (!sectors.get(currentSector).contains(position.getX() + velocity.getX(), position.getY() + velocity.getY())) {
            velocity = new Vector2(
                    (Math.random() * 2 - 1) * getMaxSpeed(),
                    (Math.random() * 2 - 1) * getMaxSpeed()
            );
        }

        if (!oldVelocity.equals(velocity) && bounce++ > 3) {
            bounce = 0;
            int oldSector = currentSector;
            while (oldSector == currentSector) {
                currentSector = rollSector();
            }

            velocity = new Vector2(
                    sectors.get(currentSector).getX() + position.getX() + Math.random() * 10,
                    sectors.get(currentSector).getY() + position.getY() + Math.random() * 10
            );
            velocity.scale(1/Math.sqrt(velocity.distanceToSquared(position)));
            wanderingToCurrentSector = true;
        }

        position.add(velocity);
    }

    /**
     * Losuje sektor na podstawie czterokrotnego "rzutu kością" o 2 ścianach
     *
     * W ten sposób otrzymujemy różne szanse na wylosowanie każdego z sektorów w ciekawym rozkładzie - ma to symulować
     * częściej odwiedzane lokacje (np. dom, praca, sklep) oraz mniej odwiedzane (np. klub)
     * @return numer wylosowanego sektora
     */
    public int rollSector() {
        int value = 0;
        for (int i = 0; i < 4; ++i) {
            value += rand.nextInt(3);
        }
        return Math.max(0, -2 + value);
    }

    /**
     * Ustawia listę sektorów
     * @param sectors lista sektorów
     */
    public void setSectors(List<Rectangle> sectors) {
        this.sectors = sectors;
    }



    /**
     * Zwraca pobliskie osoby
     * @param map Mapa symulacji
     * @return Pobliskie osoby
     */
    public List<Person> getNearby(SimMap map) {
        List<Person> result = new ArrayList<>();
        for (Person person : map.getPeople()) {
            if (person.equals(this)) {
                continue;
            }

            if (person.getPosition().distanceToSquared(this.getPosition()) <= 9){
                result.add(person);
            }
        }

        return result;
    }

    /**
     * Aktualizuje postęp rozwoju wirusa u zarażonego oraz zaraża pobliskie osoby
     * @param map mapa symulacji
     */
    public void update(SimMap map) {
        if (incubationPeriod > 0) {
            incubationPeriod--;
        }

       for (Person person : getNearby(map)) {
           if (!person.isInfected() && canInfect(person)) {
               if (person.wasInfected()){
                   person.setInfected(Math.random() > 0.5);
                   continue;
               }

               person.setInfected(true);
           }
       }
    }

    /**
     * Ustawia pozycje osoby
     * @param position pozycja osoby
     */
    public void setPosition(Vector2 position) {
        this.position = position;
    }

    /**
     * Zwraca pozycje osoby
     * @return pozycja osoby
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Ustawia stan śmierci osoby
     * @param died stan śmierci osoby
     */
    public void setDied(boolean died) {
        this.died = died;
    }

    /**
     * Sprawdza czy osoba była wcześniej zarażona (i została uleczona)
     * @return stan ozdrowienia
     */
    public boolean wasInfected() {
        return !infected && incubationPeriod == 0;
    }

    /**
     * Koryguje poziom śmiertelności wirusa który nieznacząco zmienia przebieg symulacji
     * @param lethalityLevel śmiertelność (1-10)
     */
    public void setLethalityLevel(double lethalityLevel) {
        this.lethalityLevel = lethalityLevel;
    }

    /**
     * Ustawia podstawową wartość czasu inkubacji nadawaną przy zarażeniu
     * @param incubationValue czas inkubacji
     */
    public void setIncubationValue(int incubationValue) {
        this.incubationValue = incubationValue;
    }
}
