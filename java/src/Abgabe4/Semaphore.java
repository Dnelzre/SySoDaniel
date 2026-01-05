package Abgabe4;


public class Semaphore {

    private int permits;

    public Semaphore() {
        this(1);
    }

    public Semaphore(int initialPermits) {
        if (initialPermits <= 0) {
            throw new IllegalArgumentException("Initialwert muss positiv sein");
        }
        this.permits = initialPermits;
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
