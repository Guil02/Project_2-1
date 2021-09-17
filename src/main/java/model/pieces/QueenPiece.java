package model.pieces;

import model.Board;

/**
 * Queen piece
 */
public class QueenPiece extends ChessPiece {

    public QueenPiece(boolean isWhite, Board board, int index_x, int index_y) {
        super(isWhite, index_x, index_y);
    }

    public char getPieceChar() {
        if (this.isWhite)
            return 'Q';
        else
            return 'q';
    }

    public boolean[][] validMoves() {

        boolean[][] valid_moves = new boolean[8][8];
        valid_moves[2][0] = true;

        //Todo

        return valid_moves;
    }

}
