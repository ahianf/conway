package cl.ahian.conway;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;

public class GameOfLife {
    static Random random = new Random();
    int x;
    int y;
    int[][] grid;


    public GameOfLife(int x, int y) {
        this.x = x;
        this.y = y;
        grid = generarMatriz(x, y);

    }

    public int[][] apply() {


        Function<int[][], int[][]> evolve = g -> {
            int[][] newGrid = new int[g.length][g[0].length];
            for (int i = 0; i < g.length; i++) {
                for (int j = 0; j < g[i].length; j++) {
                    int alive = 0;
                    for (int ii = Math.max(0, i - 1); ii <= Math.min(g.length - 1, i + 1); ii++) {
                        for (int jj = Math.max(0, j - 1); jj <= Math.min(g[i].length - 1, j + 1); jj++) {
                            if (ii == i && jj == j) continue;
                            alive += g[ii][jj];
                        }
                    }
                    if (g[i][j] == 0 && alive == 3) newGrid[i][j] = 1;
                    else if (g[i][j] == 1 && (alive < 2 || alive > 3)) newGrid[i][j] = 0;
                    else newGrid[i][j] = g[i][j];
                }
            }
            return newGrid;
        };

        return grid = evolve.apply(grid);
    }

    private static int[][] generarMatriz(int x, int y) {

        int[][] grid = new int[x][y];
        // Llenar el array con un valor int.
        for (int[] row : grid) {
            Arrays.fill(row, generarIntRandom(0, 1));
        }


//        int[][] grid = new int[x][y];
//        final int VALOR_FILL = 1;
//        // Llenar el array con un valor int.
//        for (int[] row : grid) {
//            Arrays.fill(row, VALOR_FILL);
//        }

//        System.out.println("");
//        for (int i = 1; i <= x; i++) {
//            for (int j = 1; j <= y; j++) {
//                System.out.println(i + ", " + j);
//            }
//        }

//        imprimirArray(grid);

        return grid;
    }

    private static void imprimirArray(int[][] grid) {
        // Imprimir array
        for (int[] row : grid) {
            for (int i : row) {
                System.out.print(i);
            }
            System.out.print("\n");
        }
        System.out.println("///");

    }

    public static int generarIntRandom(int min, int max) {
        max += 1;
        return random.nextInt(max - min) + min;
    }
}
