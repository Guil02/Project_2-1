package model.pieces;

public class RookPiece extends Piece {

    public RookPiece(boolean white) {
        if (white)
            setPieceChar('R');
        else
            setPieceChar('r');
    }
}
