package Abgabe4.Nr2;

public class DiagrammAufgabe {

    static class Semaphore {
        private int permits;

        public Semaphore(int initial) {
            permits = initial;
        }

        public synchronized void acquire() throws InterruptedException {
            while (permits == 0) {
                wait();
            }
            permits--;
        }

        public synchronized void release() {
            permits++;
            notifyAll();
        }
    }

    // Semaphore für Pfeil
    static Semaphore s12 = new Semaphore(0);
    static Semaphore s13 = new Semaphore(0);
    static Semaphore s14 = new Semaphore(0);
    static Semaphore s25 = new Semaphore(0);
    static Semaphore s35 = new Semaphore(0);
    static Semaphore s45 = new Semaphore(0);
    static Semaphore s51 = new Semaphore(1); // Startbedingung

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            try {
                while (true) {
                    s51.acquire();
                    System.out.println("a1");
                    s12.release();
                    s13.release();
                    s14.release();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                while (true) {
                    s12.acquire();
                    System.out.println("a2");
                    s25.release();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread t3 = new Thread(() -> {
            try {
                while (true) {
                    s13.acquire();
                    System.out.println("a3");
                    s35.release();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread t4 = new Thread(() -> {
            try {
                while (true) {
                    s14.acquire();
                    System.out.println("a4");
                    s45.release();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread t5 = new Thread(() -> {
            try {
                while (true) {
                    s25.acquire();
                    s35.acquire();
                    s45.acquire();
                    System.out.println("a5");
                    s51.release();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }
}

