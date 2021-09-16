package model.pieces;

/**
 * Pawn piece
 */
public class PawnPiece extends ChessPiece {


    @Override
    public boolean[][] validMoves() {
        return new boolean[0][];
    }

    @Override
    public char getPieceChar() {
        return 0;
    }
}
