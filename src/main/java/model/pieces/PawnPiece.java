package model.pieces;

public class PawnPiece extends Piece {
    public PawnPiece(boolean white) {
        if (white)
            setPieceChar('P');
        else
            setPieceChar('p');
    }
}
