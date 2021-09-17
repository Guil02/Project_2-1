package model.pieces;

import model.Board;

//TODO when board created (all positions of all pieces known) --> move 1 in diagonal to eat the opponent piece

/**
 * Pawn piece
 */
public class PawnPiece extends ChessPiece {

    public PawnPiece(boolean white, Board board, int index_x, int index_y) {
        super(white, index_x, index_y);
    }

    public char getPieceChar() {
        if (this.isWhite)
            return 'P';
        else
            return 'p';
    }

    public boolean[][] validMoves() {

        boolean[][] valid_moves = new boolean[8][8];
        valid_moves[2][0] = true;
        /*
        if(index_v == 1) {
            valid_moves[this.index_h][this.index_v + 2] = true;
        }
        if((0 <= (this.index_v )) && ((this.index_v + 1) < Board.BOARDSIZE)) {
            valid_moves[this.index_h][this.index_v + 1] = true;
        }

         */
        return valid_moves;
    }
}
