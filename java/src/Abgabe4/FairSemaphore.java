package Abgabe4;
import java.util.LinkedList;
import java.util.Queue;

public class FairSemaphore {

    private final Semaphore semaphore;
    private final Queue<Thread> waitingQueue = new LinkedList<>();

    public FairSemaphore() {
        this(1);
    }

    public FairSemaphore(int initialPermits) {
        semaphore = new Semaphore(initialPermits);
    }

    public void acquire() throws InterruptedException {
        Thread currentThread = Thread.currentThread();

        synchronized (this) {
            waitingQueue.add(currentThread);
        }

        synchronized (this) {
            while (waitingQueue.peek() != currentThread) {
                wait();
            }
        }

        semaphore.acquire();

        synchronized (this) {
            waitingQueue.remove();
        }
    }

    public void release() {
        semaphore.release();

        synchronized (this) {
            notifyAll();
        }
    }
}
