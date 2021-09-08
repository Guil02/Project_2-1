package model.pieces;

/**
 * King piece
 */
public class KingPiece extends Piece {

    /**
     * Constructor
     * @param white
     */
    public KingPiece(boolean white) {
        if (white)
            setPieceChar('K');
        else
            setPieceChar('k');
    }
}
