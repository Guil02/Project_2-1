package model.pieces;

/**
 * Pawn piece
 */
public class PawnPiece extends Piece {

    /**
     * Constructor
     * @param white
     */
    public PawnPiece(boolean white) {
        if (white)
            setPieceChar('P');
        else
            setPieceChar('p');
    }
}
