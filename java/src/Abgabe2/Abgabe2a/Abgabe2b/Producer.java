package Abgabe2.Abgabe2a.Abgabe2b;

public class Producer extends Thread {
    private Stack stack;

    public Producer(Stack stack, String name) {
        super(name);
        this.stack = stack;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            stack.push(i);
            try {
                Thread.sleep((int) (Math.random() * 400));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
