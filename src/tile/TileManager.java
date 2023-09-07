package tile;

import java.awt.Graphics2D;
import java.awt.List;
import java.util.ArrayList;
import java.util.Random;

import main.GamePanel;
import tools.Score;

public class TileManager {
    
    final private static Random randomGen = new Random();

    final protected static int maxFrameCountMove = 5;
    final protected static int maxFrameCountMerge = 3;

    protected static Tile[][] board;

    protected static boolean animatingMove = false;
    protected static boolean animatingSpawn = false;

    protected static int animationFrame;

    protected static ArrayList<Tile> tempTiles;

    public static void initTileManager() {

        board = new Tile[4][4];
    
    }

    public static void move(int direction) {

        if (animatingMove) {

            animationFrame = maxFrameCountMove;
            GamePanel.deferMove();
            return;

        } else if (animatingSpawn) {

            animationFrame = maxFrameCountMerge;
            GamePanel.deferMove();

            return;
        }

        TileMove tMove = new TileMove(direction);

        tMove.move();
        tMove.applyMove();

    }

    protected static boolean updateLocations() {

        boolean changed = false;

        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 4; row++) {
                try {

                    if (board[col][row].handleMove(col, row)) {
                        changed = true;
                    }
                    
                } catch (NullPointerException ex) {}
            }

        }

        animatingMove = changed;

        return changed;
    }

    private static boolean checkForLoss() {

        TileMove moveCheck;
        
        for (int direction = 0; direction < 4; direction++) {

            moveCheck = new TileMove(direction);

            if (moveCheck.checkForChange()) {
                return false;
            }

        }

        return true;

    }

    public static boolean isAnimating() {
        return animatingSpawn || animatingMove;
    }

    protected static void addTempTile(Tile t) {

        tempTiles.add(t);

    }

    public static Tile getTile(int col, int row) { 
        return board[col][row];
    }

    public static void update() {

        if (animatingMove) {
            
            if (animationFrame < maxFrameCountMove) {

                animationFrame++;

            } else {

                animatingMove = false;
                animatingSpawn = true;
                animationFrame = 0;

                tempTiles = null;
                
                addNewRandomTile(true);

                

            }

        } else if (animatingSpawn) {
            
            if (animationFrame < maxFrameCountMerge) {

                animationFrame++;

            } else {

                if (getAvailableSlots().getItemCount() == 0) {
                    // This means there is a full board
                    if (checkForLoss()) {
                        Score.onLoss();
                    }
                }

                animationFrame = 0;
                animatingSpawn = false;

            }

        }

    }

    public static void draw(Graphics2D g2d) {
        
        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 4; row++) {
                try {
                    board[col][row].draw(g2d);
                } catch (NullPointerException ex) {}
            }
        }

        try {
            for (Tile tile:tempTiles) {
                tile.draw(g2d);
            }
        } catch (NullPointerException ex) {}
        

    }

    protected static void merge(Tile t) {

        int value = t.getValue()*2;
        board[t.col][t.row] = Tile.newTile(value, t.col, t.row);

        Score.addScore(value);

    }

    private static void addTile(int col, int row) {
        board[col][row] = Tile.newTile(col, row);
    }

    public static void addNewRandomTile(Boolean canRecurse) {

        int col, row;
        
        List availableSlots = getAvailableSlots();
        int slotChosen;

        if (availableSlots.getItemCount() < 1) {
            return;
        }

        try {
            slotChosen = randomGen.nextInt(availableSlots.getItemCount());
        } catch (IllegalArgumentException ex) {
            // This means the board is full and another piece cannot be added
            return;
        }

        availableSlots.select(slotChosen);

        col = availableSlots.getSelectedItem().charAt(0) - '0';
        row = availableSlots.getSelectedItem().charAt(2) - '0';

        addTile(col, row);

        if (canRecurse) {
            if (randomGen.nextDouble() > 0.8) {
                addNewRandomTile(false);
            }
        }

    }

    private static List getAvailableSlots() {

        List availableTiles = new List();

        for (int col = 0; col < 4; col++) {

            for (int row = 0; row < 4; row++) {

                if (board[col][row] == null) {

                    availableTiles.add(col+","+row);

                }

            }

        }

        return availableTiles;

    }

    public static Tile[][] getBoard() {

        return board;

    }

}
