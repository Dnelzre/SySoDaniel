package Abgabe2.Abgabe2a.Abgabe2b;

public class Stack {
    private int[] stack;
    private int position = 0;

    public Stack(int n) {
        stack = new int[n];
    }

    public synchronized void push(int item) {
        while (position == stack.length) {
            try {
                System.out.println(Thread.currentThread().getName() + " wartet: Stack ist voll");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Element hinzufügen
        stack[position] = item;
        position++;
        System.out.println(Thread.currentThread().getName() + " hat " + item + " gepusht");

        // Wartende Threads benachrichtigen
        notifyAll();
    }

    public synchronized int pop() {
        while (position == 0) {
            try {
                System.out.println(Thread.currentThread().getName() + " wartet: Stack ist leer");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        position--;
        int item = stack[position];
        System.out.println(Thread.currentThread().getName() + " hat " + item + " gepoppt");

        notifyAll();
        return item;
    }
}
