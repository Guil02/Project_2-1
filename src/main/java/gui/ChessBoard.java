package gui;

import javafx.scene.layout.GridPane;

public class ChessBoard extends GridPane {
    ChessSpot[] board = new ChessSpot[64];

    public ChessBoard() {
        for(int i = 0; i<64; i++){
            int x = i%8;
            int y = (i-x)/8;
            ChessSpot spot = new ChessSpot(this,x,y);
            add(spot,x,y);
            board[i]=spot;
        }

    }

    public ChessSpot[] getBoard() {
        return board;
    }

    public ChessSpot getChessSpot(int x, int y){
        int index = y*8+x;
        return getBoard()[index];
    }
}
