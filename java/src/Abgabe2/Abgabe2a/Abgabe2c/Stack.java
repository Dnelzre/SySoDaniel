package Abgabe2.Abgabe2a.Abgabe2c;

//notifyAll() weckt alle Threads auf, die auf das Object warten
// bei Notify wird nur einer Geweckt, dieser führt dann zum Deadlock da wenn ein weiterer Producer geweckt werden kann obwohl der stack noch voll ist.
//Begründung mit Coffman:
//Mutual Exclusion:Push und Pop sidn synchronized , also kann nur ein stack zugreifen
//Hold and Wait: Thread hält den Thread-Lock und wartet
//No Preemtion: Thread gibt lock erst frei wenn wait() aufgerufen wird
//Circular Wait: Mehrere Producer warten,dass COnsumer Arbeiten, aber Producer werden geweckt -> keiner kann arbeiten
// Da alles davon gilt entsteht eine Deadlock


public class Stack {
    private int[] stack;
    private int position = 0;

    public Stack(int n) {
        stack = new int[n];
    }

    public synchronized void push(int item) {
        while (position == stack.length) {
            try {
                System.out.println(Thread.currentThread().getName() + " wartet: Stack voll");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        stack[position++] = item;
        System.out.println(Thread.currentThread().getName() + " hat " + item + " gepusht");
        notifyAll();
    }

    public synchronized int pop() {
        while (position == 0) {
            try {
                System.out.println(Thread.currentThread().getName() + " wartet: Stack leer");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        int item = stack[--position];
        System.out.println(Thread.currentThread().getName() + " hat " + item + " gepoppt");
        notifyAll();
        return item;
    }
}
