package model.pieces;

/**
 * Class that represents a single piece on the board.
 * Needs a concrete piece type for instantiation.
 */
public abstract class Piece {
    private char pieceChar;

    /**
     * Returns the current char of the piece
     * Upper case = white piece
     * Lower case = black piece
     * (default naming)
     * @return Letter of the current piece
     */
    public char getPieceChar() {
        return pieceChar;
    }

    protected void setPieceChar(char pieceChar) {
        this.pieceChar = pieceChar;
    }
}
