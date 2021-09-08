package model.pieces;

public class KingPiece extends Piece {

    public KingPiece(boolean white) {
        if (white)
            setPieceChar('K');
        else
            setPieceChar('k');
    }
}
