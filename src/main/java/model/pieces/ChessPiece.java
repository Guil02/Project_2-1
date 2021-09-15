package model.pieces;

import model.Board;

public abstract class ChessPiece {

    protected boolean isWhite;
    protected int index_x;
    protected int index_y;
    protected Board currentBoard;

    public ChessPiece() {

    }

    public ChessPiece(boolean isWhite, int index_x, int index_y) {

        this.isWhite = isWhite;
        this.index_x = index_x;
        this.index_y = index_y;

        //TODO show piece
    }

    public int getIndex_x() {
        return this.index_x;
    }

    public int getIndex_y() {
        return this.index_y;
    }

    public void move(int index_x, int index_y) {

        currentBoard.getBoardUpdater().movePiece(this.index_x, this.index_y, index_x, index_y); // Sets position on the board
        // Updates internal position
        this.index_x = index_x;
        this.index_y = index_y;


        //TODO show new piece position
    }

    public abstract boolean[][] validMoves(ChessPiece[][] chessPieces);

    public abstract char getPieceChar();
}
