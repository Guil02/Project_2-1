package model.pieces;

/**
 * Queen piece
 */
public class QueenPiece extends Piece {

    /**
     * Constructor
     * @param white
     */
    public QueenPiece(boolean white) {
        if (white)
            setPieceChar('Q');
        else
            setPieceChar('q');
    }
}
