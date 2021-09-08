package model.pieces;

/**
 * Bishop piece
 */
public class BishopPiece extends Piece {

    /**
     * Constructor
     * @param white
     */
    public BishopPiece(boolean white) {
        if (white)
            setPieceChar('B');
        else
            setPieceChar('b');
    }
}
