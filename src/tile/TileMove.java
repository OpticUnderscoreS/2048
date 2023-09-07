package tile;

import java.util.ArrayList;

public class TileMove {

    private int direction;

    private int starting;
    private int iteration;

    private boolean vertical;

    private Tile[] path;

    private Tile[][] board;

    protected TileMove(int direction) {

        this.direction = direction;
        board = new Tile[][] {
            TileManager.board[0].clone(),
            TileManager.board[1].clone(),
            TileManager.board[2].clone(),
            TileManager.board[3].clone()
        };

        initVariables();

    }

    private void initVariables() {

        if (direction % 2 > 0) {
            starting = 3;
            iteration = -1;
        } else {
            starting = 0;
            iteration = 1;
        }

        vertical = direction < 2;

    }

    protected boolean checkForChange() {

        boolean change = false;
        
        move();

        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 4; row++) {

                if (board[col][row] != null) {

                    if (board[col][row].hasUnhandledMove()) {

                        board[col][row].swallowMove();
                        change = true;
                        
                    }
                    
                }

            }

        }

        return change;

    } 

    protected boolean applyMove() {

        TileManager.board = board;
        return TileManager.updateLocations();

    }
    
    protected void move() {

        TileManager.tempTiles = new ArrayList<Tile>();

        for (int i = 0; i < 4; i++) {

            getPath(i);

            for (int j = 0; j < 4; j++) {

                if (path[j] != null) {
                    path = path[j].move(path, j);
                }

            }

            if (vertical) {

                for (int j = 0; j < 4; j++) {
                    board[i][j] = path[starting + iteration*j];
                }
                
            } else {

                for (int j = 0; j < 4; j++) {
                    board[j][i] = path[starting + iteration*j];
                }

            }

        }

    }

    private void getPath(int index) {

        if (vertical) {

            path = new Tile[] {
                board[index][starting],
                board[index][starting + iteration],
                board[index][starting + iteration*2],
                board[index][starting + iteration*3]
            };
                
        } else {
            
            path = new Tile[] {
                board[starting][index],
                board[starting + iteration][index],
                board[starting + iteration*2][index],
                board[starting + iteration*3][index]
            };

        }

    }

}
