package gui;

import javafx.scene.layout.GridPane;

/**
 * is the layout manager for the chess game itself
 */
public class ChessBoard extends GridPane {
    ChessSpot[] board = new ChessSpot[64];

    /**
     * a constructor that will create a 8x8 board for the chess game to take place on
     */
    public ChessBoard() {
        for(int i = 0; i<64; i++){
            int x = i%8;
            int y = (i-x)/8;
            ChessSpot spot = new ChessSpot(this,x,y);
            add(spot,x,y);
            board[i]=spot;
        }

    }

    /**
     * @return an array that contains all the spots on the board and can the easily be gone through.
     */
    public ChessSpot[] getBoard() {
        return board;
    }

    /**
     * translates two-dimensional coordinates into the 1 dimensional coordinate that can then be used to access the array.
     *
     * @param x the x coordinate of the spot on the board you want to get returned.
     * @param y the y coordinate of the spot on the board you want to get returned.
     * @return the spot on the board that is located at the specified x and y coordinates.
     */
    public ChessSpot getChessSpot(int x, int y){
        int index = y*8+x;
        return getBoard()[index];
    }
}
