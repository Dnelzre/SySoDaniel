package Abgabe5;
import java.util.concurrent.Semaphore;

    public class DiningMain {
        public static void main(String[] args) {
            int N = 5;
            Semaphore[] fork = new Semaphore[N];
            for (int i = 0; i < N; i++) fork[i] = new Semaphore(1);

            Semaphore butler = new Semaphore(N - 1);

            for (int i = 0; i < N; i++) {
                new Philosopher(
                        fork[i],
                        fork[(i + 1) % N],
                        butler
                ).start();
            }
        }
    }


