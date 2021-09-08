package model.pieces;

/**
 * Rook piece
 */
public class RookPiece extends Piece {

    /**
     * Constructor
     * @param white
     */
    public RookPiece(boolean white) {
        if (white)
            setPieceChar('R');
        else
            setPieceChar('r');
    }
}
