package Abgabe3.Aufgabe1;


import java.util.concurrent.Semaphore;
import java.util.concurrent.ArrayBlockingQueue;

class Cookie {}
class Package {
    public final int cookies;
    public Package(int c) { this.cookies = c; }
}




class CookieQueue extends ArrayBlockingQueue<Cookie> {
    public CookieQueue(int size) { super(size); }
}





class PackageQueue extends ArrayBlockingQueue<Package> {
    public PackageQueue(int size) { super(size); }
}






class Rezeptbuch {
    public int getBackzeit() {
        return 1000 + (int)(Math.random() * 1500);
    }
}



class Bakery {
    private final CookieQueue cookieQueue;
    private final Statistik stats;
    private final Semaphore devices;

    public Bakery(CookieQueue cq, int geraete, Statistik st) {
        this.cookieQueue = cq;
        this.stats = st;
        this.devices = new Semaphore(geraete, true);
    }
    public void changeBakingDevices(int neu) {
        int delta = neu - devices.availablePermits();
        if (delta > 0) devices.release(delta);
        System.out.println("Backgeräte geändert auf: " + neu);
    }




    public void bake(int dauer) throws InterruptedException {
        long start = System.currentTimeMillis();
        devices.acquire();                // Elf wartet
        long wait = System.currentTimeMillis() - start;
        Thread.sleep(dauer);

        cookieQueue.put(new Cookie());

        stats.recordBake(wait);

        devices.release();
    }
}




class Elf extends Thread {
    private final Bakery bakery;
    private final Rezeptbuch rezept;
    private volatile boolean running = true;

    public Elf(String name, Bakery bakery, Rezeptbuch rezept) {
        super(name);
        this.bakery = bakery;
        this.rezept = rezept;
    }
    public void stopRunning() { running = false; interrupt(); }
    @Override
    public void run() {
        while (running) {
            try {
                bakery.bake(rezept.getBackzeit());
            } catch (InterruptedException e) {}
        }
    }
}





class Packer extends Thread {
    private final CookieQueue cookieQueue;
    private final PackageQueue packageQueue;
    private final Statistik stats;
    private volatile boolean running = true;

    public Packer(String name, CookieQueue cq, PackageQueue pq, Statistik st) {
        super(name);
        this.cookieQueue = cq;
        this.packageQueue = pq;
        this.stats = st;
    }

    public void stopRunning() { running = false; interrupt(); }

    @Override
    public void run() {
        while (running) {
            try {
                Cookie c1 = cookieQueue.take();
                Cookie c2 = cookieQueue.take();
                Cookie c3 = cookieQueue.take();
                Cookie c4 = cookieQueue.take();
                Cookie c5 = cookieQueue.take();

                packageQueue.put(new Package(5));
                stats.recordPackage();

            } catch (InterruptedException e) {}
        }
    }
}





class Decorator extends Thread {
    private static final Semaphore leftTools  = new Semaphore(3, true);
    private static final Semaphore rightTools = new Semaphore(3, true);
    private final PackageQueue pkgQueue;
    private final Statistik stats;
    private volatile boolean running = true;

    public Decorator(String name, PackageQueue pq, Statistik st) {
        super(name);
        this.pkgQueue = pq;
        this.stats = st;
    }

    public void stopRunning() { running = false; interrupt(); }
    @Override
    public void run() {
        while (running) {
            try {
                Package p = pkgQueue.take();

                long start = System.currentTimeMillis();
                acquireTools();
                long wait = System.currentTimeMillis() - start;

                Thread.sleep(300 + (int)(Math.random() * 600));

                stats.recordDecorate(wait);
                releaseTools();
            } catch (InterruptedException e) {}
        }
    }

    private void acquireTools() throws InterruptedException {
        if (System.identityHashCode(leftTools) < System.identityHashCode(rightTools)) {
            leftTools.acquire();
            rightTools.acquire();
        } else {
            rightTools.acquire();
            leftTools.acquire();
        }
    }

    private void releaseTools() {
        leftTools.release();
        rightTools.release();
    }
}
