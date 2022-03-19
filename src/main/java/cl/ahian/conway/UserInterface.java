/* (C)2022 - Ahian Fernández Puelles*/
package cl.ahian.conway;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

class UserInterface extends JPanel {
    static Logger logger = Logger.getLogger(UserInterface.class.getName());
    static final int FRAME_WIDTH = 1920;
    static final int FRAME_HEIGHT = 1080;
    static final int SCALING_FACTOR = 2;

    int chrono = 1000 / 60;
    int counter = 0;

    static BufferedImage canvas = new BufferedImage(FRAME_WIDTH, FRAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);
    Conway conway = new Conway(FRAME_WIDTH / SCALING_FACTOR, FRAME_HEIGHT / SCALING_FACTOR);
    static JFrame frame = new JFrame("JConway");

    public static void main(String[] args) {
        createAndShowGUI();
        logger.log(Level.INFO, "Iniciado");
    }

    public UserInterface() {
        setPreferredSize(new Dimension(canvas.getWidth(), canvas.getHeight()));
    }

    private void runAnimation() {
        while (true) {
            long startTime = System.nanoTime();
            boolean[][] grid = conway.apply();
            drawArray(grid);
            repaint();
            long duration = (System.nanoTime() - startTime);
            counter++;

            frame.setTitle("JConway | FPS: " + 1000000000 / duration + " Generation: " + counter);
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

        logger.log(Level.INFO, "Tamaño de ventana: " + FRAME_WIDTH + ", " + FRAME_HEIGHT + ". Scaling: " + SCALING_FACTOR + "x");
        logger.log(Level.INFO, "Tamaño de grid : " + FRAME_WIDTH / SCALING_FACTOR + ", " + FRAME_HEIGHT / SCALING_FACTOR);
        animationPanel.runAnimation();
    }

    private static int getRefreshRate() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getDisplayMode().getRefreshRate();
    }

    private static void drawArray(boolean[][] grid) {
        final int whiteRGB = Color.WHITE.getRGB();
        final int redRGB = Color.RED.getRGB();

        for (int row = 0; row < grid.length; row++) {
            //            System.out.println();
            for (int col = 0; col < grid[row].length; col++) {
                //                System.out.print(grid[row][col]);

                if (grid[row][col]) {

                    canvas.setRGB((row * 2), (col * 2), redRGB);
                    canvas.setRGB((row * 2) + 1, (col * 2), redRGB);

                    canvas.setRGB((row * 2), (col * 2) + 1, redRGB);
                    canvas.setRGB((row * 2) + 1, (col * 2) + 1, redRGB);
                } else {

                    canvas.setRGB((row * 2), (col * 2), whiteRGB);
                    canvas.setRGB((row * 2) + 1, (col * 2), whiteRGB);

                    canvas.setRGB((row * 2), (col * 2) + 1, whiteRGB);
                    canvas.setRGB((row * 2) + 1, (col * 2) + 1, whiteRGB);
                }
            }
        }
    }

}