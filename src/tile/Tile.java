package tile;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Random;

import gui.Board;
import tools.Colours;
import tools.Fonts;

public class Tile {

    final private static Random randomGen = new Random();
    
    final private static int width = Board.size;
    final private static int height = Board.size;
    final private static int arc = 20;
    
    private int value;
    private int visibleValue;
    private RoundRectangle2D visual;

    public Point location;
    public Point visibleLocation;
    public Point nLocation;

    private double pixelsPerFrame;

    private boolean movingVertical = true;

    private boolean animatingMove = false;

    private boolean unhandledMove = false;
    private boolean unhandledMerge = false;

    protected int col;
    protected int row;

    protected Tile mergingWith;

    private Tile(int value, int col, int row) {

        this.value = value;
        visibleValue = value;

        this.col = col;
        this.row = row;
        
        setLocation(Board.getTileLocation(col, row));

    }


    protected static Tile newTile(int col, int row) {
        
        Tile t = new Tile((randomGen.nextDouble() <= 0.9) ? 2 : 4, col, row);

        return t;

    }

    protected static Tile newTile(int col, int row, int value) {

        Tile t = new Tile(col, row, value);

        return t;

    }

    private void setLocation(Point location) {

        this.location = location;
        visibleLocation = location;

        visual = new RoundRectangle2D.Double(width, height, location.x, location.y, arc, arc);

    }

    public int getValue() {
        return visibleValue;
    }

    public boolean handleMove(int col, int row) {

        if (unhandledMove) {

            if (unhandledMerge) {
                mergingWith.handleMove(col, row);
            }

            this.col = col;
            this.row = row;

            nLocation = Board.getTileLocation(col, row);
            
            movingVertical = nLocation.y != location.y;

            animatingMove = true;

            double displacement = (double) (nLocation.x+nLocation.y - (location.x+location.y));
            
            pixelsPerFrame =  displacement / (double) (TileManager.maxFrameCountMove);
            
            unhandledMove = false;

            return true;

        }

        return false;
        
    }

    public boolean hasUnhandledMove() {
        return unhandledMove;
    }

    public void swallowMove() {
        unhandledMove = false;
        // Check to see if needs to swallow Merge as well

        if (unhandledMerge) {
            // Swallows Merge
            mergingWith = null;
            unhandledMerge = false;
            value = visibleValue;
        }
    }

    public Tile[] move(Tile[] path, int position) {

        if (position-1 < 0) {

            return path;
            
        }

        if (path[position-1] == null) {

            path[position-1] = this;
            path[position] = null;

            unhandledMove = true;

            return move(path, position-1);
        }

        if (path[position-1].value == value && path[position-1].value != -1) {

            mergingWith = path[position-1];
            TileManager.addTempTile(path[position-1]);

            path[position-1] = this;
            path[position] = null;

            unhandledMove = true;
            unhandledMerge = true;

            value = -1;

            return path;

        }

        return path;

    }

    

    private void merge() {

        TileManager.merge(this);

    }

    public void draw(Graphics2D g2d) {

        if (animatingMove) {
            nextFrame();
        }

        g2d.setColor(Colours.TILES.get(visibleValue));

        visual.setFrame(visibleLocation.x, visibleLocation.y, width, height);

        g2d.fill(visual);

        drawValue(g2d);

    }

    private void drawValue(Graphics2D g2d) {

        String numValue = String.valueOf(visibleValue);
        Float fontSize = 64f - numValue.length()*6;

        Font font = Fonts.getTileFont(fontSize);

        Rectangle2D fontBounds = font.getStringBounds(numValue, g2d.getFontRenderContext());

        FontMetrics fontInfo = g2d.getFontMetrics(font);
        
        int textHeight = fontInfo.getHeight();
        int textWidth = fontInfo.stringWidth(numValue);

        int x, y;

        x = (width - textWidth) / 2;
        x += visibleLocation.x;

        y = (height - textHeight)/2;
        y = height - y;
        y += visibleLocation.y;
        y -= fontBounds.getY() + fontBounds.getHeight();

        g2d.setFont(font);
        
        g2d.setColor(Color.white);
        g2d.drawString(numValue, x, y);

    }
    
    private void nextFrame() {

        if (TileManager.animatingMove) {

            if (movingVertical) {
                visibleLocation.translate(0, (int)(pixelsPerFrame));
            } else {
                visibleLocation.translate((int)(pixelsPerFrame), 0);
            }
            
        } else {

            animatingMove = false;

            visibleLocation.setLocation(nLocation);
            location.setLocation(nLocation);
            nLocation = null;

            if (unhandledMerge) {

                merge();

            }

        }

    }

}
