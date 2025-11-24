package Abgabe3.Aufgabe1.b;

import Abgabe3.Aufgabe1.b.Bakery;

public class Elf extends Thread {

    private String name;
    private Bakery bakery;

    public Elf(String name, Bakery bakery) {
        this.name = name;
        this.bakery = bakery;
    }

    @Override
    public void run() {
        while (true) {
            bakery.bakeCookie(name);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
