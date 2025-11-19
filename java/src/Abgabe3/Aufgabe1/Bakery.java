package Abgabe3.Aufgabe1;


    public class Bakery {
        public synchronized void bakeCookie(String elfsName) {
            System.out.println(elfsName + " wartet auf die Ressource...");
            System.out.println(elfsName + " backt einen Keks...");
            try {
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println(elfsName + " hat einen Keks fertig gebacken!");
        }
    }
