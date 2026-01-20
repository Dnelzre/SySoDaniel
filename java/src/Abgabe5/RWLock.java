package Abgabe5;

public interface RWLock {
    void beginRead() throws InterruptedException;
    void endRead();
    void beginWrite() throws InterruptedException;
    void endWrite();
}