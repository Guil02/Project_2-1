package model.pieces;

import model.Board;

public class Bishop extends Piece {

	public Bishop(boolean white, int index_h, int index_v) {
		super(white, index_h, index_v);
		if(white){
			setPieceChar('B');
		}
		else setPieceChar('b');
	}

	public boolean[][] validMoves() {
			
		boolean[][] valid_moves = new boolean[Board.BOARDSIZE][Board.BOARDSIZE];
			
			// first diagonal
			for(int i = -Board.BOARDSIZE; i<= Board.BOARDSIZE; i++) {
				if(!(i==0)) {
					if((0 <= (this.index_h + i )) && ((this.index_h + i) < Board.BOARDSIZE) && (0 <= (this.index_v + i )) && ((this.index_v + i) < Board.BOARDSIZE)){
						valid_moves[this.index_h + i][this.index_v + i] = true;
					}
				}
			}
			// second diagonal
			for(int i = -Board.BOARDSIZE; i<= Board.BOARDSIZE; i++) {
				if(!(i==0)) {
					if((0 <= (this.index_h + i )) && ((this.index_h - i) < Board.BOARDSIZE) && (0 <= (this.index_v + i )) && ((this.index_v + i) < Board.BOARDSIZE)){
						valid_moves[this.index_h + i][this.index_v - i] = true;
					}
				}
			}
			return valid_moves;
	}


}
