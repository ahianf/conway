/* (C)2022 - Ahian Fernández Puelles*/
package cl.ahian.conway;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

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
        File selectedFile = null;
        JFileChooser chooser = new JFileChooser();

        int errorCounter = 0;
        while (errorCounter < 3) {
            chooser.setFileFilter(new FileNameExtensionFilter("Run Length Encoded file", "rle", "RLE"));
            chooser.showOpenDialog(null);
            selectedFile = chooser.getSelectedFile();

            if (selectedFile == null) {
                JOptionPane.showMessageDialog(
                        null, "Please select a RLE file", "Error", JOptionPane.ERROR_MESSAGE);
                errorCounter++;
            } else {
                break;
            }
        }


        conway = new Conway(selectedFile);
        GRID_WIDTH = conway.getX();
        GRID_HEIGHT = conway.getY();
        canvas =
                new BufferedImage(
                        GRID_WIDTH * SCALING_FACTOR,
                        GRID_HEIGHT * SCALING_FACTOR,
                        BufferedImage.TYPE_INT_RGB);
        setPreferredSize(new Dimension(canvas.getWidth(), canvas.getHeight()));
    }

    private void runAnimation() {
        while (true) {
            long startTime = System.nanoTime();
            drawArray(conway.apply());
            repaint();

            long duration = (System.nanoTime() - startTime);
            counter++;
            frame.setTitle(
                    "JConway | FPS: %d | Generation: %d".formatted(1000000000 / duration, counter));
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(canvas, null, null);
    }

    private static void createAndShowGUI() {

        UserInterface ui = new UserInterface();

        frame.add(ui);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pixels = ((DataBufferInt) canvas.getRaster().getDataBuffer()).getData();

        logger.info(
                "Tamaño de ventana: "
                        + GRID_WIDTH * SCALING_FACTOR
                        + ", "
                        + GRID_HEIGHT * SCALING_FACTOR
                        + ". Scaling: "
                        + SCALING_FACTOR
                        + "x");
        logger.log(Level.INFO, "Tamaño de grid : " + GRID_WIDTH + ", " + GRID_HEIGHT);
        ui.runAnimation();
    }

    private static int getRefreshRate() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getScreenDevices()[0]
                .getDisplayMode()
                .getRefreshRate();
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
