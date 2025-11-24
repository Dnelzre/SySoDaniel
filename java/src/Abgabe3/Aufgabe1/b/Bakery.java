package Abgabe3.Aufgabe1.b;

import java.util.concurrent.Semaphore;

public class Bakery {

    private final Semaphore bakingDevices = new Semaphore(4);

    public void bakeCookie(String elfsName) {
        try {
            System.out.println(elfsName + " möchte reservieren");
            bakingDevices.acquire();
            System.out.println(elfsName + " hat erhalten");
            System.out.println(elfsName + " backt gerade");
            Thread.sleep((int)(Math.random() * 1000));
            System.out.println(elfsName + " fertig");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            System.out.println(elfsName + " gibt frei");
            bakingDevices.release();
        }
    }
}
//Wenn mehr elfen als geräte da sind dann staut sich die Produktion und es entstehen wartezeiten
//Wenn mehr oder gleich viele Geräte da sind als Elfen kann jeder elf so viel produzieren wie schnell er ist, da keine wartezeiten da sind

