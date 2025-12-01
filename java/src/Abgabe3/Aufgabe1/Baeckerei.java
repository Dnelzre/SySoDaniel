package Abgabe3.Aufgabe1;

import java.util.*;

class Statistik {

    private long baked = 0;
    private long bakeWait = 0;
    private long packages = 0;
    private long decorated = 0;
    private long decorWait = 0;



    public synchronized void recordBake(long w) {
        baked++; bakeWait += w;
    }


    public synchronized void recordPackage() {
        packages++;
    }


    public synchronized void recordDecorate(long w) {
        decorated++; decorWait += w;
    }

    public synchronized void print() {

        System.out.println("Kekse gebacken: " + baked);
        System.out.println("Ø Back-Wartezeit: " + (baked > 0 ? bakeWait / baked : 0));
        System.out.println("Pakete verpackt: " + packages);
        System.out.println("Pakete dekoriert: " + decorated);
        System.out.println("Ø Deko-Wartezeit: " + (decorated > 0 ? decorWait / decorated : 0)); //immer 0 ka wieso, schrödingers code
    }
}

class StatistikReporter extends Thread {
    private final Statistik st;
    private boolean running = true;

    public StatistikReporter(Statistik st) { this.st = st; }

    public void stopReporter() { running = false; interrupt(); }

    @Override
    public void run() {
        while (running) {
            st.print();
            try { Thread.sleep(5000); } catch (InterruptedException e) {}
        }
    }
}

class ResourceManager {

    private final List<Elf> elves = new ArrayList<>();
    private final List<Packer> packers = new ArrayList<>();
    private final List<Decorator> decos = new ArrayList<>();

    private final Statistik stats = new Statistik();
    private final Rezeptbuch rezept = new Rezeptbuch();

    private final CookieQueue cookieQueue = new CookieQueue(50);
    private final PackageQueue packageQueue = new PackageQueue(30);

    private final Bakery bakery;

    public ResourceManager(int geraete) {
        this.bakery = new Bakery(cookieQueue, geraete, stats);
    }

    public synchronized void addElf() {
        Elf e = new Elf("Elf-" + (elves.size()+1), bakery, rezept);
        elves.add(e);
        e.start();
    }

    public synchronized void removeElf() {
        if (!elves.isEmpty()) {
            Elf e = elves.remove(elves.size()-1);
            e.stopRunning();
        }
    }

    public synchronized void addPacker() {
        Packer p = new Packer("Packer-" + (packers.size()+1), cookieQueue, packageQueue, stats);
        packers.add(p);
        p.start();
    }

    public synchronized void addDecorator() {
        Decorator d = new Decorator("Decor-" + (decos.size()+1), packageQueue, stats);
        decos.add(d);
        d.start();
    }

    public synchronized void changeBakingDevices(int newCount) {
        bakery.changeBakingDevices(newCount);
    }

    public Statistik getStatistik() { return stats; }
}
