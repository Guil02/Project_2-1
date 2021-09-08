package model.pieces;

public class KnightPiece extends Piece {
    public KnightPiece(boolean white) {
        if (white)
            setPieceChar('N');
        else
            setPieceChar('n');
    }
}
