package model.pieces;

/**
 * Rook piece
 */
public class RookPiece extends ChessPiece {

    @Override
    public boolean[][] validMoves() {
        return new boolean[0][];
    }

    @Override
    public char getPieceChar() {
        return 0;
    }
}
