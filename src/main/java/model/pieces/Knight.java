package model.pieces;

import model.Board;

public class Knight extends Piece {

	public Knight(boolean white, int index_h, int index_v) {
		super(white, index_h, index_v);
		if(white){
			setPieceChar('N');
		}
		else setPieceChar('n');
	}
	
	public boolean[][] validMoves() {
		
		boolean[][] valid_moves = new boolean[Board.BOARDSIZE][Board.BOARDSIZE];
			
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
		return valid_moves;
	}
}
			