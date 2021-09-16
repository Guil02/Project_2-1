package model.pieces;

import model.Board;

public class Rook extends Piece {

	public Rook(boolean white, int index_h, int index_v) {
		super(white, index_h, index_v);
		if(white){
			setPieceChar('R');
		}
		else setPieceChar('r');
	}

	public boolean[][] validMoves() {
		
		boolean[][] valid_moves = new boolean[Board.BOARDSIZE][Board.BOARDSIZE];
			
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
			return valid_moves;
		}
}


