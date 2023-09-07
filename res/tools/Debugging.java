package tools;

import tile.Tile;
import tile.TileManager;

public class Debugging {
    
    public static void printBoard() throws NullPointerException {

        Tile[][] board = TileManager.getBoard();

        System.out.println("------------");
        String printable;
        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 4; row++) {
                printable = (board[row][col] != null) ? String.valueOf(board[row][col].getValue()) : "-";
                System.out.print(" " + printable + " ");
                
            }
            System.out.println();
        }
        System.out.println("------------");
                
    }

}
