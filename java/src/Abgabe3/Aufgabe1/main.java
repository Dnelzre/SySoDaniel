package Abgabe3.Aufgabe1;

public class main {

        public static void main(String[] args) {
            Bakery bakery = new Bakery();

            for (int i = 1; i <= 5; i++) {
                Elf elf = new Elf(bakery, "Elf " + i);
                elf.start();
            }
        }

}
