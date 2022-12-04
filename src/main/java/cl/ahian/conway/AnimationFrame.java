package cl.ahian.conway;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class AnimationFrame extends JPanel {

    long animDuration = 3600000;
    long currentTime = System.nanoTime() / 1000000;
    long startTime = currentTime;
    long elapsedTime = currentTime - startTime;
    int chrono = 1000 / refreshRate();
    static BufferedImage canvas;
    GameOfLife gol = new GameOfLife(1920, 1080);



    public AnimationFrame() {
        setPreferredSize(new Dimension(canvas.getWidth(), canvas.getHeight()));
    }

    public void runAnimation() {
        while (elapsedTime < animDuration) {
            currentTime = System.nanoTime() / 1000000;
            elapsedTime = currentTime - startTime;
            System.out.println(elapsedTime);
            int[][] grid = gol.apply();
            drawArray(grid);
            try {
                Thread.sleep(chrono);
            } catch (Exception e) {
            }
            repaint();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(canvas, null, null);
    }

    public static void main(String[] args) {
        createAndShowGUI();
    }

    public static void createAndShowGUI() {
        int width = 1920;
        int height = 1080;
        JFrame frame = new JFrame("Direct draw demo");
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        fillCanvas(Color.WHITE);
        AnimationFrame animationPanel = new AnimationFrame();

        frame.add(animationPanel);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //I made the call to runAnimation here now
        //after the containing frame i visible.
        animationPanel.runAnimation();
    }

    public static void fillCanvas(Color c) {
        int color = c.getRGB();
        for (int x = 0; x < canvas.getWidth(); x++) {
            for (int y = 0; y < canvas.getHeight(); y++) {
                canvas.setRGB(x, y, color);
            }
        }
    }

    private static int refreshRate() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getDisplayMode().getRefreshRate();

    }

    public void drawArray(int[][] grid) {
        int redRGB = Color.RED.getRGB();
        int whiteRGB = Color.WHITE.getRGB();
        for (int i = 0; i < canvas.getWidth(); i++) {
            for (int j = 0; j < canvas.getHeight(); j++) {
                if (grid[i][j] == 1) {
                    canvas.setRGB(i, j, redRGB);
                } else {
                    canvas.setRGB(i, j, whiteRGB);
                }
            }
        }
    }

}
