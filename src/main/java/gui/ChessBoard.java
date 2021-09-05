package gui;

import javafx.scene.layout.GridPane;

public class ChessBoard extends GridPane {
    ChessSpot[] board = new ChessSpot[64];
    private boolean whiteKingCastling = false;
    private boolean blackKingCastling = false;
    private boolean whiteQueenCastling = false;
    private boolean blackQueenCastling = false;
    private boolean isWhiteMove = true;

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

    public boolean isWhiteKingCastling() {
        return whiteKingCastling;
    }

    public void setWhiteKingCastling(boolean whiteKingCastling) {
        this.whiteKingCastling = whiteKingCastling;
    }

    public boolean isBlackKingCastling() {
        return blackKingCastling;
    }

    public void setBlackKingCastling(boolean blackKingCastling) {
        this.blackKingCastling = blackKingCastling;
    }

    public boolean isWhiteQueenCastling() {
        return whiteQueenCastling;
    }

    public void setWhiteQueenCastling(boolean whiteQueenCastling) {
        this.whiteQueenCastling = whiteQueenCastling;
    }

    public boolean isBlackQueenCastling() {
        return blackQueenCastling;
    }

    public void setBlackQueenCastling(boolean blackQueenCastling) {
        this.blackQueenCastling = blackQueenCastling;
    }

    public boolean isWhiteMove() {
        return isWhiteMove;
    }

    public void setWhiteMove(boolean whiteMove) {
        isWhiteMove = whiteMove;
    }
}
