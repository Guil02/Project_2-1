package model.pieces;

public class QueenPiece extends Piece {
    public QueenPiece(boolean white) {
        if (white)
            setPieceChar('Q');
        else
            setPieceChar('q');
    }
}
