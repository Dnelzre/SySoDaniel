package Abgabe5;
import java.util.concurrent.Semaphore;

class Philosopher extends Thread {
    private final Semaphore left, right;
    private final int id;

    Philosopher(int id, Semaphore left, Semaphore right) {
        this.id = id;
        this.left = left;
        this.right = right;
    }

    public void run() {
        try {
            while (true) {
                think();

                if (id % 2 == 0) {
                    left.acquire();
                    right.acquire();
                } else {
                    right.acquire();
                    left.acquire();
                }

                eat();

                left.release();
                right.release();
            }
        } catch (InterruptedException ignored) {}
    }

    void think() {}
    void eat() {}
}
