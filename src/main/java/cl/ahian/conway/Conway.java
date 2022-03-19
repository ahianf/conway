/* (C)2022 - Ahian Fernández Puelles*/
package cl.ahian.conway;

import java.util.Arrays;
import java.util.Random;

public class Conway {
    static Random random = new Random();
    int x;
    int y;
    boolean[][] grid;

    public Conway(int x, int y) {
        this.x = x;
        this.y = y;
        grid = generarMatriz(x, y);
    }

    public boolean[][] apply() {
        boolean[][] newGrid = new boolean[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                int alive = 0;
                for (int ii = Math.max(0, i - 1); ii <= Math.min(grid.length - 1, i + 1); ii++) {
                    for (int jj = Math.max(0, j - 1);
                            jj <= Math.min(grid[i].length - 1, j + 1);
                            jj++) {
                        if (ii == i && jj == j) {
                            continue;
                        }
                        alive = alive + ((grid[ii][jj]) ? 1 : 0);
                    }
                }
                if (!grid[i][j] && alive == 3) {
                    newGrid[i][j] = true;
                } else if (grid[i][j] && (alive < 2 || alive > 3)) {
                    newGrid[i][j] = false;
                } else {
                    newGrid[i][j] = grid[i][j];
                }
            }
        }
        grid = newGrid;
        return newGrid;
    }

    public static boolean[][] generarMatriz(int x, int y) {
        System.out.println("llamado!");
        boolean[][] grid = new boolean[x][y];

        // Llenar el array con un valores aleatorios.
        for (boolean[] row : grid) {
            Arrays.fill(row, random.nextBoolean());
        }

        //        for (int i = 500; i < grid.length / 2; i++) {
        //            Arrays.fill(grid[i], true);
        //        }
        return grid;
    }
}
