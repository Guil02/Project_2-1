package model.pieces;// TODO check if king doesn't put himself in danger

import model.Board;

/**
 * King piece
 */
public class KingPiece extends ChessPiece {

	public KingPiece(boolean white, Board board, int index_x, int index_y) {
		super(white, index_x, index_y, board);
	}

	public char getPieceChar() {
		if (this.isWhite)
			return 'K';
		else
			return 'k';
	}

	public boolean[][] validMoves() {

		boolean[][] valid_moves = new boolean[8][8];
		if(isTurn()){

			if (withinBounds(index_x, 1)){
				if(withinBounds(index_y,1)&&isOpenSpot(index_x+1,index_y+1)){
					valid_moves[index_x+1][index_y+1]=true;
				}
				if(withinBounds(index_y,-1)&&isOpenSpot(index_x+1,index_y-1)){
					valid_moves[index_x+1][index_y-1]=true;
				}
				if(isOpenSpot(index_x+1,index_y)){
					valid_moves[index_x+1][index_y]=true;
				}
			}
			if(withinBounds(index_x,-1)){
				if(withinBounds(index_y,1)&&isOpenSpot(index_x-1,index_y+1)){
					valid_moves[index_x-1][index_y+1]=true;
				}
				if(withinBounds(index_y,-1)&&isOpenSpot(index_x-1,index_y-1)){
					valid_moves[index_x-1][index_y-1]=true;
				}
				if(isOpenSpot(index_x-1,index_y)){
					valid_moves[index_x-1][index_y]=true;
				}
			}
			if(withinBounds(index_y,1)&&isOpenSpot(index_x,index_y+1)){
				valid_moves[index_x][index_y+1]=true;
			}
			if(withinBounds(index_y,-1)&&isOpenSpot(index_x,index_y-1)){
				valid_moves[index_x][index_y-1]=true;
			}
		}

		//small rook
		if(this.isWhite) {
			if(this.index_x == 4 && this.index_y == 7) { //checking if the king is at his initial position
				if( (this.currentBoard.getBoardModel()[7][7] != null) && (this.currentBoard.getBoardModel()[7][7].getPieceChar() == 'R') ) { //checking if the right rook is at his initial position
					//if(this.isSafe(this.index_x, this.index_y)) { //checking if the king not in a position of danger before doing the rook
						if(isOpenSpot(this.index_x + 1, this.index_y)) { //checking if no pieces on the way between the king and the rook + if the king doesn't put himself in danger while passing
							//if(this.isSafe(this.index_x + 1, this.index_y)) {
								if(isOpenSpot(this.index_x + 2, this.index_y)) {
									//if(this.isSafe(this.index_x + 2, this.index_y)) {
										//if(this.isSafe(this.index_x + 3, this.index_y)) { //checking that king doesn't put himself in danger after doing the rook
											valid_moves[7][7] = true;
										}
									}
								}
							}
						//}
					//}
				//}
			//}
		}

		return valid_moves;
	}
}
