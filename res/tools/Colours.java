package tools;

import java.awt.Color;
import java.util.HashMap;

/*
 * Resource File for all the colours used in this game
 */

public class Colours {
    
    // All the Static Colours (Colours that won't change throughout the game)
    final public static Color BACKGROUND = new Color(220, 220, 220);
    final public static Color EMPTYTILEBACKGROUND = new Color(170, 170, 170);
    final public static Color EMPTYTILEBORDER = new Color(200, 200, 200);

    // Hashmap of all the Tile Colours
    final public static HashMap<Integer, Color> TILES = new HashMap<Integer, Color>();

    public static void initColours() {

        // Colours for each Value
        TILES.put(2, new Color(255, 235, 181));
        TILES.put(4, new Color(212, 153, 51));
        TILES.put(8, new Color(240, 144, 120));
        TILES.put(16, new Color(213, 237, 133));
        TILES.put(32, new Color(90, 199, 159));
        TILES.put(64, new Color(76, 207, 200));
        TILES.put(128, new Color(92, 107, 237));
        TILES.put(256, new Color(122, 28, 230));
        TILES.put(512, new Color(237, 104, 235));
        TILES.put(1024, new Color(240, 115, 200));
        TILES.put(2048, new Color(189, 57, 83));
        
    }
}
