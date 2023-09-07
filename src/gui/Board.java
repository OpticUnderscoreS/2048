package gui;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.RoundRectangle2D;

import main.GamePanel;
import tools.Colours;

public class Board {
    
    public static RoundRectangle2D[][] tiles = new RoundRectangle2D[4][4];
    public static int margins[] = new int[] {25, 25, 140, 25}; // {Left, Right, Top, Bottom}
    public static int gap = 10;

    public static RoundRectangle2D background = new RoundRectangle2D.Double(
        margins[0], 
        margins[2], 
        GamePanel.size.width - margins[0] - margins[1], 
        GamePanel.size.width - margins[0] - margins[1], 
        20, 20
    );

    public static int size = GamePanel.size.width - (margins[0] + margins[1]) - gap*5;
    
    public static RoundRectangle2D copyTile(int col, int row) {
        return tiles[col][row];
    }

    public static void initBoard() {

        size /= 4;

        for (int col = 0; col < 4; col++) {

            for (int row = 0; row < 4; row++) {

                tiles[col][row] = new RoundRectangle2D.Double((col+1)*gap + margins[0] + col*size, (row+1)*gap + margins[2] + row*size, size, size, 20, 20);

            }

        }

    }

    public static Point getTileLocation(int col, int row) {

        return new Point((col+1)*gap + margins[0] + col*size, (row+1)*gap + margins[2] + row*size);

    }

    public static void draw(Graphics2D g2d) {

        g2d.setColor(Colours.EMPTYTILEBORDER);

        g2d.fill(background);

        g2d.setColor(Colours.EMPTYTILEBACKGROUND);

        for (RoundRectangle2D tArr[]:tiles) {
            for (RoundRectangle2D t : tArr) {
                g2d.fill(t);
            }
        }

        g2d.setColor(Colours.EMPTYTILEBORDER);
        
        for (RoundRectangle2D tArr[]:tiles) {
            for (RoundRectangle2D t : tArr) {
                g2d.draw(t);
            }
        }

    }

}
