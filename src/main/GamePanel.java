package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;

import gui.Board;
import tile.TileManager;
import tools.Score;

public class GamePanel extends JPanel implements Runnable {

    public static Dimension size = new Dimension(500, 600);

    private static KeyHandler keyH;

    private Thread gameThread;
    private double FPS = 60;
    
    private GamePanel() {
        
        keyH = new KeyHandler();

        addKeyListener(keyH);

        TileManager.addNewRandomTile(true);
        
    }

    public static void deferMove() {
        keyH.keyPressed = true;
    }

    public static GamePanel startGame(JFrame window) {

        GamePanel gp = new GamePanel();

        gp.setLayout(null);
        gp.setVisible(true);
        gp.setDoubleBuffered(true);
        gp.setPreferredSize(new Dimension(500, 600));
        gp.setFocusable(true);

        gp.setBackground(tools.Colours.BACKGROUND);

        window.add(gp);

        return gp;

    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();

    }

    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        
        while (gameThread != null) {

            // UPDATE
            update();

            // PAINT
            repaint();

            try {

                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {}

        }
    }

    private void boardReset() {

        TileManager.initTileManager();
        Score.onLoss();
        TileManager.addNewRandomTile(true);

        repaint();

    }

    public void update() {
        
        if (keyH.keyPressed) {

            keyH.keyPressed = false;

            if (keyH.resetPending) {
                boardReset();
                keyH.resetPending = false;
            } else {    
                TileManager.move(keyH.keyPressedDirection);
            }

        }

        TileManager.update();

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Board.draw(g2d);
        TileManager.draw(g2d);

        Score.draw(g2d);

        g2d.dispose();

    }
    
}
