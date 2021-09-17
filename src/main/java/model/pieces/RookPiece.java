package model.pieces;

import model.Board;

/**
 * Rook piece
 */
public class RookPiece extends ChessPiece {

    public RookPiece(boolean white, Board board, int index_x, int index_y) {
        super(white, index_x, index_y);
    }

    public char getPieceChar() {
        if (this.isWhite)
            return 'R';
        else
            return 'r';
    }

    public boolean[][] validMoves() {

        boolean[][] valid_moves = new boolean[8][8];
        valid_moves[2][0] = true;
        /*
        for(int i = -Board.BOARDSIZE; i<= Board.BOARDSIZE; i++) {
            if(!(i==0)) {
                if((0 <= (this.index_h + i )) && ((this.index_h + i) < Board.BOARDSIZE)){
                    valid_moves[this.index_h + i][this.index_v] = true;
                }
            }
        }
        for(int i = -Board.BOARDSIZE; i<= Board.BOARDSIZE; i++) {
            if(!(i==0)) {
                if((0 <= (this.index_v + i )) && ((this.index_v + i) < Board.BOARDSIZE)){
                    valid_moves[this.index_h][this.index_v + i] = true;
                }
            }
        }

         */
        return valid_moves;
    }
}
