package main;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import gui.Board;
import tile.TileManager;
import tools.Colours;
import tools.Fonts;
import tools.Score;

public class Main {

    public static JFrame window;
    public static GamePanel currentGamePanel;

    public static void main(String[] args) throws Exception {

        initTools();

        window = new JFrame("2048");

        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        currentGamePanel = GamePanel.startGame(window);

        currentGamePanel.startGameThread();
        window.pack();

        window.setResizable(false);
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);

    }

    private static void initTools() {

        Board.initBoard();
        Colours.initColours();
        TileManager.initTileManager();
        Fonts.initFonts();
        Score.initScore();

    }

}
