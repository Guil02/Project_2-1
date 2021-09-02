package model.pieces;

public abstract class Piece {
    private char pieceChar;

    public char getPieceChar() {
        return pieceChar;
    }

    protected void setPieceChar(char c) {
        this.pieceChar = c;
    }
}
