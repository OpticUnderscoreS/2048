package tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

import main.GamePanel;

public class Score {
    
    private static Font font = Fonts.getScoreFont(45f); // Sets font
    
    // All score information
    private static int highScore;
    private static int score;

    private static File highScoreFile; // Allows access to the highscore text file

    private static String path = Paths.get("").toAbsolutePath().toString() + "\\res\\gameplay\\";
    
    public static void initScore() {

        // Sets the highscore to that which is stored in the file
        try {
            highScore = getHighscore();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Triggered whenever the board is reset or when the game is lost
    public static void onLoss() {

        // Checks to see if the score of the game is higher than the current highscore
        if (score > highScore) {
            try {
                // Updates the highscore
                updateHighscore();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        score = 0; // Resets the score

    }

    private static void updateHighscore() throws IOException {

        highScore = score; // Sets the Highscore to the score

        // Sets up writers for the file
        FileWriter rawWriter = new FileWriter(highScoreFile);
        BufferedWriter writer = new BufferedWriter(rawWriter);

        writer.write(String.valueOf(highScore)); 

        writer.close();

    }

    private static int getHighscore() throws IOException {

        // Declares the reader object variables
        FileReader rawReader;
        BufferedReader reader;

        String highscoreText; // Declares the variable that will read the highscore from the file

        highScoreFile = new File(path + "highscore.txt");

        // If the highscore file doesn't exist it will create a new file under the same name
        if (!highScoreFile.exists()) {
            highScoreFile.createNewFile();
        }

        // Instantiates the readers
        rawReader = new FileReader(highScoreFile);
        reader = new BufferedReader(rawReader);

        highscoreText = reader.readLine(); // Gets the highscore from the file

        // Closes the readers
        rawReader.close();
        reader.close();

        // If the highscore text isn't readable, set it to the default of "0"
        if (highscoreText == null) {
            return 0;
        } else {
            return Integer.parseInt(highscoreText);
        }

    }
 
    public static void addScore(int score) {
        // Adds the value of the tile to the Score
        Score.score += score;
    }

    public static void draw(Graphics2D g2d) {

        // Sets the font
        g2d.setFont(font);

        FontMetrics fontInfo = g2d.getFontMetrics(font); // Instantiates the fontInfo object
        
        double textHeight = fontInfo.getHeight(); // Gets the maximum height of the text

        // Variables that will be used in the displaying of the text
        int displayScore;
        String label;
        int x, y;
        
        // Sets up the variables for the first loop
        displayScore = score;
        label = "Score: ";

        for (int i = 0; i < 2; i++) {

            g2d.setColor(Color.gray); // Sets the colour of the shadow

            double textWidth = fontInfo.stringWidth(label + displayScore); // Gets the width of the text
            
            // Gets the position of the bottom left of the text
            x = (int) (GamePanel.size.getWidth() - 25 - textWidth); // Formula for the left alignment
            y = (int) (textHeight + i*(textHeight));
            
            // Draws the shadow
            g2d.drawString(label + displayScore, x+2, y+2);

            // Draws the actual text
            g2d.setColor(Color.black);
            g2d.drawString(label + displayScore, x, y);

            // Sets the variables for the second loop/line
            displayScore = highScore; 
            label = "Highscore: ";
        }

    }

}