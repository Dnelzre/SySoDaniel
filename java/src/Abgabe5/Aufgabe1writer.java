package Abgabe5;

public class Aufgabe1writer implements RWLock {
        private int readers = 0;
        private int waitingWriters = 0;
        private boolean writerActive = false;

        public synchronized void beginRead() throws InterruptedException {
            while (writerActive || waitingWriters > 0) {
                wait();
            }
            readers++;
        }

        public synchronized void endRead() {
            readers--;
            if (readers == 0) notifyAll();
        }

        public synchronized void beginWrite() throws InterruptedException {
            waitingWriters++;
            while (writerActive || readers > 0) {
                wait();
            }
            waitingWriters--;
            writerActive = true;
        }

        public synchronized void endWrite() {
            writerActive = false;
            notifyAll();
        }
    }

