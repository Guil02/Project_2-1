package model.pieces;

/**
 * Knight piece
 */
public class KnightPiece extends Piece {

    /**
     * Constructor
     * @param white
     */
    public KnightPiece(boolean white) {
        if (white)
            setPieceChar('N');
        else
            setPieceChar('n');
    }
}
