/* (C)2022 - Ahian Fernández Puelles*/
package cl.ahian.conway;

import java.io.*;

public class Biblioteca {

    public boolean[][] getTest() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./src/main/resources/file.txt"));
        boolean[][] grid = parseLines(reader);
        return grid;
    }

    private static boolean[][] parseLines(BufferedReader reader) throws IOException {
        boolean[][] grid = new boolean[187][187];
        String line = reader.readLine();
        int counter = 0;
        while (line != null) {
            String[] stringArray = line.split("[,]");
            int arrayLength = stringArray.length;
            boolean[] booleanArray = new boolean[arrayLength];

            for (int i = 0; i < arrayLength; i++) {
                if (stringArray[i].equals("0")) {
                    booleanArray[i] = false;
                } else if (stringArray[i].equals("1")) {
                    booleanArray[i] = true;
                } else {
                    throw new RuntimeException("Valor inválido");
                }
            }
            grid[counter] = booleanArray;
            line = reader.readLine();
            counter++;
        }
        reader.close();
        return grid;
    }
}
