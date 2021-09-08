package model.pieces;

public class BishopPiece extends Piece {
    public BishopPiece(boolean white) {
        if (white)
            setPieceChar('B');
        else
            setPieceChar('b');
    }
}
