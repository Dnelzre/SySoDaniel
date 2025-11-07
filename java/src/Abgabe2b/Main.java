package Abgabe2b;

public class Main {
    public static void main(String[] args) {
        Stack stack = new Stack(5);

    //10 Producer und Consumer
        for (int i = 0; i < 10; i++) {
            new Producer(stack, "Producer-" + i).start();
            new Consumer(stack, "Consumer-" + i).start();
        }
    }
}
