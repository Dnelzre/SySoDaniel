package Abgabe3.Aufgabe1;

class Elf extends Thread {
    private final Bakery bakery;
    private final String elfName;


    public Elf(Bakery bakery, String name) {
        this.bakery = bakery;
        this.elfName = name;
    }


    @Override
    public void run() {
        while (true) {
            bakery.bakeCookie(elfName);
            try {
                Thread.sleep(500); // kurze Pause vor dem nächsten Keks
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}