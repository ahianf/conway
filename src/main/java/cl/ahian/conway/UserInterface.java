/* (C)2022 - Ahian Fernández Puelles*/
package cl.ahian.conway;

import java.awt.*;
import java.awt.image.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

class UserInterface extends JPanel {
    static Logger logger = Logger.getLogger(UserInterface.class.getName());
    static int GRID_WIDTH;
    static int GRID_HEIGHT;
    static final int SCALING_FACTOR = 2;

    int chrono = 1000 / getRefreshRate();
    static int counter = 0;

    static int[] pixels;
    static Conway conway;
    static BufferedImage canvas;

    static JFrame frame = new JFrame("JConway");

    public static void main(String[] args) {
        createAndShowGUI();
        logger.log(Level.INFO, "Iniciado");
    }

    public UserInterface() {
        conway = new Conway();
        GRID_WIDTH = conway.getX();
        GRID_HEIGHT = conway.getY();

        canvas = new BufferedImage(GRID_WIDTH * SCALING_FACTOR, GRID_HEIGHT * SCALING_FACTOR, BufferedImage.TYPE_INT_RGB);
        setPreferredSize(new Dimension(canvas.getWidth(), canvas.getHeight()));
    }

    public static int fillCanvas() {
        int rgb = Color.WHITE.getRGB();
        for (int x = 0; x < canvas.getWidth(); x++) {
            for (int y = 0; y < canvas.getHeight(); y++) {
                fastSetRGB(x, y, rgb);
            }
        }

        return 1;
    }

    private void runAnimation() {
        fillCanvas();
        while (true) {
            long startTime = System.nanoTime();
            boolean[][] grid = conway.apply();
            drawArray(grid);
            repaint();

            try {
                Thread.sleep(chrono);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            long duration = (System.nanoTime() - startTime);

            counter++;

            frame.setTitle("JConway " +
                    "|" +
//                    "FPS: " + 1000000000 / duration +
                    " Generation: " + counter);

        }
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(canvas, null, null);
    }

    private static void createAndShowGUI() {
        UserInterface animationPanel = new UserInterface();

        frame.add(animationPanel);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pixels = ((DataBufferInt) canvas.getRaster().getDataBuffer()).getData();

        logger.log(Level.INFO, "Tamaño de ventana: " + GRID_WIDTH * SCALING_FACTOR + ", " + GRID_HEIGHT * SCALING_FACTOR + ". Scaling: " + SCALING_FACTOR + "x");
        logger.log(Level.INFO, "Tamaño de grid : " + GRID_WIDTH + ", " + GRID_HEIGHT);
        animationPanel.runAnimation();
    }

    private static int getRefreshRate() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getDisplayMode().getRefreshRate();
    }

    private static void drawArray(boolean[][] grid) {
        final int whiteRGB = Color.WHITE.getRGB();
        final int redRGB = Color.RED.getRGB();

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col]) {
//                    fastSetRGB((row), (col), redRGB);

                    fastSetRGB((row * 2), (col * 2), redRGB);
                    fastSetRGB((row * 2) + 1, (col * 2), redRGB);

                    fastSetRGB((row * 2), (col * 2) + 1, redRGB);
                    fastSetRGB((row * 2) + 1, (col * 2) + 1, redRGB);
                } else {
//                    fastSetRGB((row ), (col), whiteRGB);

                    fastSetRGB((row * 2), (col * 2), whiteRGB);
                    fastSetRGB((row * 2) + 1, (col * 2), whiteRGB);

                    fastSetRGB((row * 2), (col * 2) + 1, whiteRGB);
                    fastSetRGB((row * 2) + 1, (col * 2) + 1, whiteRGB);
                }
            }
        }
    }

    public static void fastSetRGB(int x, int y, int rgb) {
        int pos = (x) + (y * GRID_WIDTH * SCALING_FACTOR);
        pixels[pos] = rgb;
    }
}
