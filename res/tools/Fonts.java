package tools;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/*
 *  This is a resource file to allow quick access and the ability to change the size of a font without the deriveFont method
 */

public class Fonts {

    // Path that will be used to access all Font files
    final private static String PATH = Paths.get("").toAbsolutePath().toString() + "\\res\\fonts\\";

    // Font Declaration
    private static Font tileFont;
    private static Font scoreFont;
    
    public static void initFonts() {

        // Instantiates all fonts

        try {
            tileFont = Font.createFont(Font.TRUETYPE_FONT, new File(PATH + "Rubik-Bold.ttf"));
            scoreFont = Font.createFont(Font.TRUETYPE_FONT, new File(PATH + "Rubik-Regular.ttf"));
        } catch (FontFormatException | IOException e) {
            System.out.println(e);
        }

    }

    // Allows access to the fonts with control of the size

    public static Font getTileFont(float size) {
        return tileFont.deriveFont(size);
    }

    public static Font getScoreFont(float size) {
        return scoreFont.deriveFont(size);
    }

}
