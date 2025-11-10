package Abgabe2nr2;

public class GameOfLife {
    public static void main(String[] args) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                //kleineres Grid weil mein Laptop das sonst nicht schafft :(
                TheGrid g = TheGrid.BingBang(200, 200, -1, 0.5);
                g.simulateGenerationsParallel(100);
                System.out.println("Survivors: " + g.numberOfSurvivors());
                if (g.isStatic()) {
                    System.out.println("The grid is static");
                } else {
                    System.out.println("The grid is still living and not static");
                }
            }
        };

        double executionTime = Stopwatch.startstop(r);
        System.out.println("Execution time: " + executionTime + " ms");
    }
}