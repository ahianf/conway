/* (C)2022 - Ahian Fernández Puelles*/
package cl.ahian.conway;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Biblioteca {
    static Logger logger = Logger.getLogger(Biblioteca.class.getName());

    public boolean[][] getTest(File path) throws IOException {

        //        BufferedReader reader = new BufferedReader(new
        // FileReader("./src/main/resources/file.txt"));
        //        boolean[][] grid = parseLines(reader);
        return read(path);
    }

    public static boolean[][] read(File file) {
        long startTime = System.nanoTime();
        logger.log(Level.INFO, "Iniciando lectura de: '" + file.getAbsolutePath() + "'");
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        String line = scanner.nextLine();
        for (; line.charAt(0) == '#'; line = scanner.nextLine()) {
            ; // Para saltar los comentarios
        }

        Pattern pattern = Pattern.compile("^x = ([0-9]+), y = ([0-9]+), rule = (.+)$");
        Matcher matcher = pattern.matcher(line);

        if (!matcher.matches()) {
            return null;
        }

        int n = Integer.parseInt(matcher.group(1));
        int m = Integer.parseInt(matcher.group(2));
        logger.log(Level.INFO, "Tamaño grid RLE: " + "x: " + n + ", y: " + m);

        boolean[][] array = new boolean[m][n];
        int buffer = 0;
        int x = 0, y = 0;

        do {
            line = scanner.nextLine();
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                if (Character.isDigit(c)) {
                    buffer = (10 * buffer) + (c - '0');
                } else if (c == 'o') {
                    Arrays.fill(array[y], x, Math.min(n, x + Math.max(1, buffer)), true);
                    x += Math.max(1, buffer);
                    buffer = 0;
                } else if (c == 'b') {
                    x += Math.max(1, buffer);
                    buffer = 0;
                } else if (c == '$') {
                    y += Math.max(1, buffer);
                    x = 0;
                    buffer = 0;
                } else if (c == '!') {
                    long endTime = System.nanoTime();
                    long duration = (endTime - startTime);
                    logger.info(
                            "RLE leído con éxito, conversión demoró " + duration / 1000000 + " ms");
                    return array;
                } else {
                    return null;
                }
            }
        } while (scanner.hasNext());

        return null;
    }
}
