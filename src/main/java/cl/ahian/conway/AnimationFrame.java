/* (C)2022 - Ahian Fernández Puelles*/
package cl.ahian.conway;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

class AnimationFrame extends JPanel {

    static final int FRAME_WIDTH = 1280;
    static final int FRAME_HEIGHT = 720;

    int chrono = 1000 / refreshRate();
    int counter = 0;

    static BufferedImage canvas =
            new BufferedImage(FRAME_WIDTH, FRAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);

    Conway conway = new Conway(FRAME_WIDTH, FRAME_HEIGHT);
    static JFrame frame = new JFrame("JConway");

    public static void main(String[] args) {
        createAndShowGUI();
    }

    public AnimationFrame() {
        setPreferredSize(new Dimension(canvas.getWidth(), canvas.getHeight()));
    }

    private void runAnimation() {
        while (true) {
            long startTime = System.nanoTime();
            boolean[][] grid = conway.apply();
            drawArray(grid);
            long duration = (System.nanoTime() - startTime);
            counter++;
            try {
                Thread.sleep(chrono);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            repaint();
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
        AnimationFrame animationPanel = new AnimationFrame();

        frame.add(animationPanel);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        animationPanel.runAnimation();
    }

    private static int refreshRate() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getScreenDevices()[0]
                .getDisplayMode()
                .getRefreshRate();
    }

    public static void drawArray(boolean[][] grid) {
        final int whiteRGB = Color.WHITE.getRGB();
        final int redRGB = Color.RED.getRGB();

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col]) {
                    canvas.setRGB(row, col, redRGB);
                } else {
                    canvas.setRGB(row, col, whiteRGB);
                }
            }
        }
    }
}
