package Abgabe5;

public class Aufgabe1reader implements RWLock {
    private int readers = 0;
    private boolean writerActive = false;

    public synchronized void beginRead() throws InterruptedException {
        while (writerActive) {
            wait();
        }
        readers++;
    }

    public synchronized void endRead() {
        readers--;
        if (readers == 0) notifyAll();
    }

    public synchronized void beginWrite() throws InterruptedException {
        while (writerActive || readers > 0) {
            wait();
        }
        writerActive = true;
    }

    public synchronized void endWrite() {
        writerActive = false;
        notifyAll();
    }
}
