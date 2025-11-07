package Abgabe2b;

public class Stack {
    private int[] stack;
    private int position = 0;

    public Stack(int n) {
        stack = new int[n];
    }

    // push-Methode (legt ein Element auf den Stack)
    public synchronized void push(int item) {
        // Warten, solange der Stack voll ist
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

    // pop-Methode (nimmt ein Element vom Stack)
    public synchronized int pop() {
        // Warten, solange der Stack leer ist
        while (position == 0) {
            try {
                System.out.println(Thread.currentThread().getName() + " wartet: Stack ist leer");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Element entnehmen
        position--;
        int item = stack[position];
        System.out.println(Thread.currentThread().getName() + " hat " + item + " gepoppt");

        // Wartende Threads benachrichtigen
        notifyAll();
        return item;
    }
}
