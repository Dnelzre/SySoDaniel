package Abgabe2nr2;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TheGrid {

    private int horizontal_dimension;
    private int vertical_dimension;
    private boolean[][] grid;

    // Anzahl Threads = verfügbare CPU-Kerne
    private static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors();

    public TheGrid(int horizontal_dimension, int vertical_dimension) {
        this.horizontal_dimension = horizontal_dimension;
        this.vertical_dimension = vertical_dimension;
        this.grid = new boolean[horizontal_dimension][vertical_dimension];
    }

    public void setCell(int x, int y, boolean value) {
        this.grid[x][y] = value;
    }

    public boolean getCell(int x, int y) {
        return this.grid[x][y];
    }

    public int countLivingNeighbors(int x, int y) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int dx = (x + i + horizontal_dimension) % horizontal_dimension;
                int dy = (y + j + vertical_dimension) % vertical_dimension;
                if (grid[dx][dy]) count++;
            }
        }
        return count;
    }

    public boolean nextCellState(int x, int y) {
        int livingNeighbors = countLivingNeighbors(x, y);
        boolean cellState = getCell(x, y);
        if (cellState) {
            return livingNeighbors == 2 || livingNeighbors == 3;
        } else {
            return livingNeighbors == 3;
        }
    }

    // 🔹 Parallele Berechnung der nächsten Generation
    public TheGrid nextGenerationParallel() {
        TheGrid next = new TheGrid(horizontal_dimension, vertical_dimension);
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        int rowsPerThread = vertical_dimension / THREAD_COUNT;

        for (int t = 0; t < THREAD_COUNT; t++) {
            final int startRow = t * rowsPerThread;
            final int endRow = (t == THREAD_COUNT - 1) ? vertical_dimension : startRow + rowsPerThread;

            executor.submit(() -> {
                try {
                    for (int row = startRow; row < endRow; row++) {
                        for (int col = 0; col < horizontal_dimension; col++) {
                            next.setCell(col, row, nextCellState(col, row));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
        try {
            // Warte maximal 10 Minuten, um Deadlocks zu vermeiden
            if (!executor.awaitTermination(10, TimeUnit.MINUTES)) {
                System.err.println("⚠️ Threads did not finish in time – possible deadlock avoided.");
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return next;
    }

    // 🔹 Parallele Simulation mehrerer Generationen
    public void simulateGenerationsParallel(int iterations) {
        for (int i = 0; i < iterations; i++) {
            TheGrid next = nextGenerationParallel();
            this.grid = next.grid;
        }
    }

    // 🔹 Deadlock-sichere parallele Überprüfung, ob Grid statisch ist
    public boolean isStatic() {
        TheGrid next = nextGenerationParallel();
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        final boolean[] isStatic = {true};
        int rowsPerThread = vertical_dimension / THREAD_COUNT;

        for (int t = 0; t < THREAD_COUNT; t++) {
            final int startRow = t * rowsPerThread;
            final int endRow = (t == THREAD_COUNT - 1) ? vertical_dimension : startRow + rowsPerThread;

            executor.submit(() -> {
                try {
                    for (int row = startRow; row < endRow && isStatic[0]; row++) {
                        for (int col = 0; col < horizontal_dimension; col++) {
                            if (grid[col][row] != next.grid[col][row]) {
                                synchronized (isStatic) {
                                    isStatic[0] = false;
                                }
                                return;
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.MINUTES)) {
                System.err.println("⚠️ isStatic(): Timeout waiting for threads!");
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return isStatic[0];
    }

    public int numberOfSurvivors() {
        int count = 0;
        for (int col = 0; col < horizontal_dimension; col++) {
            for (int row = 0; row < vertical_dimension; row++) {
                if (grid[col][row]) count++;
            }
        }
        return count;
    }

    public static TheGrid BingBang(int horizontal_dimension, int vertical_dimension, int seed, double density) {
        TheGrid g = new TheGrid(horizontal_dimension, vertical_dimension);
        Random rg = seed < 0 ? new Random() : new Random(seed);
        for (int col = 0; col < horizontal_dimension; col++) {
            for (int row = 0; row < vertical_dimension; row++) {
                g.setCell(col, row, rg.nextDouble() < density);
            }
        }
        return g;
    }

    // Optional: Grid ausgeben
    public void print(PrintStream p) {
        for (int row = 0; row < vertical_dimension; row++) {
            for (int col = 0; col < horizontal_dimension; col++) {
                p.print(grid[col][row] ? "X" : ".");
            }
            p.println();
        }
        p.println();
    }
}
