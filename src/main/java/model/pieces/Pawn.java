package model.pieces;

import model.Board;

//TODO when board created (all positions of all pieces known) --> move 1 in diagonal to eat the opponent piece
public class Pawn extends Piece {

	public Pawn(boolean white, int index_h, int index_v) {
		super(white, index_h, index_v);
		if(white){
			setPieceChar('P');
		}
		else setPieceChar('p');
	}

	public boolean[][] validMoves() {
		
		boolean[][] valid_moves = new boolean[Board.BOARDSIZE][Board.BOARDSIZE];
		
		if(index_v == 1) {
			valid_moves[this.index_h][this.index_v + 2] = true;
		}
		if((0 <= (this.index_v )) && ((this.index_v + 1) < Board.BOARDSIZE)) {
				valid_moves[this.index_h][this.index_v + 1] = true;
		}
		return valid_moves;
	}
}
