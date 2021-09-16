package model.pieces;

/**
 * Queen piece
 */
public class QueenPiece extends ChessPiece {


    @Override
    public boolean[][] validMoves() {
        return new boolean[0][];
    }

    @Override
    public char getPieceChar() {
        return 0;
    }
}
