package model.pieces;

/**
 * King piece
 */
public class KingPiece extends ChessPiece {

    @Override
    public boolean[][] validMoves() {
        return new boolean[0][];
    }

    @Override
    public char getPieceChar() {
        return 0;
    }
}
