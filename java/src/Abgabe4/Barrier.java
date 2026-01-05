package Abgabe4;

public class Barrier {

    private final int parties;
    private int waiting = 0;
    private int generation = 0;

    public Barrier(int parties) {
        if (parties <= 0) {
            throw new IllegalArgumentException("Anzahl der Threads muss positiv sein");
        }
        this.parties = parties;
    }

    public synchronized void await() throws InterruptedException {
        int currentGeneration = generation;
        waiting++;

        if (waiting == parties) {
            waiting = 0;
            generation++;
            notifyAll();
            return;
        }


        while (currentGeneration == generation) {
            wait();
        }
    }
}

