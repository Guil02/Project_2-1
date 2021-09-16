package model.pieces;

/**
 * Knight piece
 */
public class KnightPiece extends ChessPiece {


    @Override
    public boolean[][] validMoves() {
        return new boolean[0][];
    }

    @Override
    public char getPieceChar() {
        return 0;
    }
}
