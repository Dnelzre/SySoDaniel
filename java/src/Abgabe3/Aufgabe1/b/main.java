package Abgabe3.Aufgabe1.b;

import Abgabe3.Aufgabe1.b.Bakery;
import Abgabe3.Aufgabe1.b.Elf;

public class main {
    public static void main(String[] args) {

        Bakery bakery = new Bakery();

        int numberOfElves = 4;

        for (int i = 1; i <= numberOfElves; i++) {
            Elf elf = new Elf("Elf " + i, bakery);
            elf.start();
        }
    }
}
