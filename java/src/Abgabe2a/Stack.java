package Abgabe2a;
public class Stack {
    private int[] stack;
    private int position = 0;

    public Stack(int n) {
        stack = new int[n];
    }

    public synchronized void push(int item) {
        while (position == stack.length) {
            try {
                System.out.println("Stack ist voll. Thread schläft...");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Element einfügen
        stack[position] = item;
        position++;
        System.out.println("Element " + item + " wurde auf den Stack gelegt.");

        notifyAll();
    }

    public synchronized int pop() {
        while (position == 0) {
            try {
                System.out.println("Stack ist leer. Thread schläft...");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        position--;
        int item = stack[position];
        System.out.println("Element " + item + " wurde vom Stack genommen.");


        notifyAll();
        return item;
    }


    public synchronized void printStack() {
        System.out.print("Aktueller Stack: ");
        for (int i = 0; i < position; i++) {
            System.out.print(stack[i] + " ");
        }
        System.out.println();
    }


    public static void main(String[] args) {
        Stack s = new Stack(3);

        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                s.push(i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {}
            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                s.pop();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {}
            }
        });

        producer.start();
        consumer.start();
    }
}