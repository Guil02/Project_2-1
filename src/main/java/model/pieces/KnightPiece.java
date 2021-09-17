package model.pieces;

import model.Board;

/**
 * Knight piece
 */
public class KnightPiece extends ChessPiece {

    public KnightPiece(boolean white, Board board, int index_x, int index_y) {
        super(white, index_x, index_y);
    }

    public char getPieceChar() {
        if (this.isWhite)
            return 'N';
        else
            return 'n';
    }

    public boolean[][] validMoves() {

        boolean[][] valid_moves = new boolean[8][8];
        valid_moves[2][0] = true;

        /*
        if((0 <= (this.index_h + 2)) && ((this.index_h + 2) < Board.BOARDSIZE) && (0 <= (this.index_v + 1 )) && ((this.index_v + 1) < Board.BOARDSIZE)){
            valid_moves[this.index_h + 2][this.index_v + 1] = true;
        }
        if((0 <= (this.index_h + 2 )) && ((this.index_h + 2) < Board.BOARDSIZE) && (0 <= (this.index_v - 1 )) && ((this.index_v - 1) < Board.BOARDSIZE)){
            valid_moves[this.index_h + 2][this.index_v - 1] = true;
        }
        if((0 <= (this.index_h - 2 )) && ((this.index_h - 2) < Board.BOARDSIZE) && (0 <= (this.index_v + 1 )) && ((this.index_v + 1) < Board.BOARDSIZE)){
            valid_moves[this.index_h - 2][this.index_v + 1] = true;
        }
        if((0 <= (this.index_h - 2 )) && ((this.index_h - 2) < Board.BOARDSIZE) && (0 <= (this.index_v - 1 )) && ((this.index_v - 1) < Board.BOARDSIZE)){
            valid_moves[this.index_h - 2][this.index_v - 1] = true;
        }
        if((0 <= (this.index_h + 1 )) && ((this.index_h + 1) < Board.BOARDSIZE) && (0 <= (this.index_v + 2 )) && ((this.index_v + 2) < Board.BOARDSIZE)){
            valid_moves[this.index_h + 1][this.index_v + 2] = true;
        }
        if((0 <= (this.index_h - 1 )) && ((this.index_h - 1) < Board.BOARDSIZE) && (0 <= (this.index_v + 2 )) && ((this.index_v + 2) < Board.BOARDSIZE)){
            valid_moves[this.index_h - 1][this.index_v + 2] = true;
        }
        if((0 <= (this.index_h + 1 )) && ((this.index_h + 1) < Board.BOARDSIZE) && (0 <= (this.index_v - 2 )) && ((this.index_v - 2) < Board.BOARDSIZE)){
            valid_moves[this.index_h + 1][this.index_v - 2] = true;
        }
        if((0 <= (this.index_h - 1 )) && ((this.index_h - 1) < Board.BOARDSIZE) && (0 <= (this.index_v - 2)) && ((this.index_v - 2) < Board.BOARDSIZE)){
            valid_moves[this.index_h - 1][this.index_v - 2] = true;
        }

         */
        return valid_moves;
    }
}