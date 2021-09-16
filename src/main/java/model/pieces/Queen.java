package model.pieces;

import model.Board;

public class Queen extends Piece {

    public Queen(boolean color, int index_h, int index_v) {
        super(color, index_h, index_v);
    }
    //TODO: this is not correct i copied it from king cause there was no queen yet
    public boolean[][] validMoves() {

        boolean[][] valid_moves = new boolean[Board.BOARDSIZE][Board.BOARDSIZE];

        for(int i = -1; i<= 1; i++) {
            for(int j = -1; j<=1; j++) {

                if(!(i==0 && j==0)) {
                    if((0 <= (this.index_h+i)) && ((this.index_h+i) < Board.BOARDSIZE)) {
                        if((0 <= (this.index_v+i)) && ((this.index_v+i) < Board.BOARDSIZE)) {
                            valid_moves[this.index_h + i][this.index_v + j] = true;
                        }
                    }
                }
            }
        }
        return valid_moves;
    }
}
