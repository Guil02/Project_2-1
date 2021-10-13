package model.pieces;

import model.Board;
import java.util.*;

/**
 * class that determines every valid moves for a king
 */
public class KingPiece extends ChessPiece {
	private boolean hasNotMoved = true;
	public KingPiece(boolean white, Board board, int index_x, int index_y) {
		super(white, index_x, index_y, board);
	}

	public char getPieceChar() {
		if (this.isWhite)
			return 'K';
		else
			return 'k';
	}

	@Override
	public void move(int new_index_x, int new_index_y) {
		this.hasNotMoved=false;
		super.move(new_index_x, new_index_y);
	}

	/**
	 * method that determines whenever a king put himself in danger when making a move
	 * @param index_x initial x position of a king on the current board
	 * @param index_y initial y position of a king on the current board
	 * @return boolean double array of all safe moves that a king can make
	 */
	private boolean[][] isSafe(int index_x, int index_y) {
		// memorize King's current position
		int oldIndex_x = this.index_x;
		int oldIndex_y = this.index_y;

		// simulate possible King's moves
		this.index_x = index_x;
		this.index_y = index_y;

		if( (this.index_x != oldIndex_x) || (this.index_y != oldIndex_y) ) {
			this.currentBoard.getBoardModel()[index_x][index_y] = this.currentBoard.getBoardModel()[oldIndex_x][oldIndex_y]; // update the new King position into the board
			this.currentBoard.getBoardModel()[oldIndex_x][oldIndex_y] = null;
		}

		ArrayList<ChessPiece> enemies; // store current piece enemies that represent a threat for the king
		if(this.isWhite) {
			enemies = this.currentBoard.getBlackPieces();
		}
		else {
			enemies = this.currentBoard.getWhitePieces();
		}

		boolean[][] isSafe = new boolean[Board.getBoardSize()][Board.getBoardSize()];
		// set all moves being safe by default
		for(boolean[] booleans : isSafe) {
			Arrays.fill(booleans, true);
		}

		// switch isTurn indicator
		if(this.isWhite){
			this.currentBoard.getGameRunner().setWhiteMove(false);
		}
		else{
			this.currentBoard.getGameRunner().setWhiteMove(true);
		}

		for(ChessPiece enemy : enemies) {
			if(enemy.getPieceChar()=='k'||enemy.getPieceChar()=='K'){
				continue;
			}
			boolean[][] tmp = enemy.validMoves(); // check if enemy is threat
			for(int j = 0; j < isSafe.length; j++) {
				for(int k = 0; k < isSafe[j].length; k++) {
					if(tmp[j][k]) {
						isSafe[j][k] = false;
					}
				}
			}
		}
		// re-switch isTurn indicator
		if(this.isWhite){
			this.currentBoard.getGameRunner().setWhiteMove(true);
		}
		else{
			this.currentBoard.getGameRunner().setWhiteMove(false);
		}

		// reassign King to his initial position
    	this.index_x = oldIndex_x;
    	this.index_y = oldIndex_y;

		if( (index_x != oldIndex_x) || (index_y != oldIndex_y) ) {
			this.currentBoard.getBoardModel()[oldIndex_x][oldIndex_y] =  this.currentBoard.getBoardModel()[index_x][index_y]; // re-updating the board as it originally was
			this.currentBoard.getBoardModel()[index_x][index_y] = null;
		}
    	return isSafe;
	}

	/*
	 * method that returns all possible positions for a king to move to w.r.t. isSafe condition
	 */
	public boolean[][] validMoves() {

		boolean[][] valid_moves = new boolean[Board.getBoardSize()][Board.getBoardSize()];

		if(isTurn()) {
			// vertical and diagonal moves (forwards)
			if (withinBounds(index_x, 1)){
				if(withinBounds(index_y,1)&&isOpenSpot(index_x+1,index_y+1)){
					valid_moves[index_x+1][index_y+1]=true;
					hasValidMove = true;
				}
				if(withinBounds(index_y,-1)&&isOpenSpot(index_x+1,index_y-1)){
					valid_moves[index_x+1][index_y-1]=true;
					hasValidMove = true;
				}
				if(isOpenSpot(index_x+1,index_y)){
					valid_moves[index_x+1][index_y]=true;
					hasValidMove = true;
				}
			}
			// vertical and diagonal moves (backwards)
			if(withinBounds(index_x,-1)){
				if(withinBounds(index_y,1)&&isOpenSpot(index_x-1,index_y+1)){
					valid_moves[index_x-1][index_y+1]=true;
					hasValidMove = true;
				}
				if(withinBounds(index_y,-1)&&isOpenSpot(index_x-1,index_y-1)){
					valid_moves[index_x-1][index_y-1]=true;
					hasValidMove = true;
				}
				if(isOpenSpot(index_x-1,index_y)){
					valid_moves[index_x-1][index_y]=true;
					hasValidMove = true;
				}
			}
			// lateral moves
			if(withinBounds(index_y,1)&&isOpenSpot(index_x,index_y+1)){
				valid_moves[index_x][index_y+1]=true;
				hasValidMove = true;
			}
			if(withinBounds(index_y,-1)&&isOpenSpot(index_x,index_y-1)){
				valid_moves[index_x][index_y-1]=true;
				hasValidMove = true;
			}
		}

		// small castling
		// whites
		if(hasNotMoved) {
			if (this.isWhite) {
				if (this.index_x == 4 && this.index_y == 7) { // checking if the king is at his initial position
					if ((this.currentBoard.getBoardModel()[7][7] != null) && (this.currentBoard.getBoardModel()[7][7].getPieceChar() == 'R')) { // checking if the left rook is at his initial position
						if(rookHasNotMoved(7,7)) {
							if (this.isSafe(this.index_x, this.index_y)[this.index_x][this.index_y]) { // checking if the king not in a position of danger before doing the castling
								if (isOpenSpot(this.index_x + 1, this.index_y)) { // checking if no pieces on the way between the king and the rook + if the king doesn't put himself in danger while passing
									if (this.isSafe(this.index_x + 1, this.index_y)[this.index_x + 1][this.index_y]) {
										if (isOpenSpot(this.index_x + 2, this.index_y)) {
											if (this.isSafe(this.index_x + 2, this.index_y)[this.index_x + 2][this.index_y]) { // checking that king doesn't put himself in danger after doing the castling
												valid_moves[6][7] = true;
											}
										}
									}
								}
							}
						}
					}
				}
			}
			// blacks
			else {
				if (this.index_x == 4 && this.index_y == 0) { // checking if the king is at his initial position
					if (this.currentBoard.getBoardModel()[7][0] != null && this.currentBoard.getBoardModel()[7][0].getPieceChar() == 'r') { //  if the left rook is at his initial position
						if(rookHasNotMoved(7,0)) {
							if (this.isSafe(this.index_x, this.index_y)[this.index_x][this.index_y]) { // checking if the king not in a position of danger before doing the castling
								if (isOpenSpot(this.index_x + 1, this.index_y)) { //checking if no pieces on the way between the king and the rook + if the king doesn't put himself in danger while passing
									if (this.isSafe(this.index_x, this.index_y)[this.index_x + 1][this.index_y]) {
										if (isOpenSpot(this.index_x + 2, this.index_y)) {
											if (this.isSafe(this.index_x, this.index_y)[this.index_x + 2][this.index_y]) { // checking that king doesn't put himself in danger after doing the castling
												valid_moves[6][0] = true;
											}
										}
									}
								}
							}
						}
					}
				}
			}

			// great castling
			// whites
			if (this.isWhite) {
				if (this.index_x == 4 && this.index_y == 7) { // checking if the king is at his initial position
					if ((this.currentBoard.getBoardModel()[0][7]) != null && (this.currentBoard.getBoardModel()[0][7].getPieceChar() == 'R')) { // checking if the right rook is at his initial position
						if(rookHasNotMoved(0,7)) {
							if (this.isSafe(this.index_x, this.index_y)[this.index_x][this.index_y]) { // checking if the king not in a position of danger before doing the castling
								if (isOpenSpot(this.index_x - 1, this.index_y)) { // checking if no pieces on the way between the king and the rook + if the king doesn't put himself in danger while passing
									if (this.isSafe(this.index_x-1, this.index_y)[this.index_x - 1][this.index_y]) {
										if (isOpenSpot(this.index_x - 2, this.index_y)) {
											if (this.isSafe(this.index_x-2, this.index_y)[this.index_x - 2][this.index_y]) { // checking that king doesn't put himself in danger after doing the castling
												if(isOpenSpot(this.index_x - 3, this.index_y)){
													if(this.isSafe(this.index_x-3, this.index_y)[this.index_x - 3][this.index_y]){
														valid_moves[2][7] = true;
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			// blacks
			else {
				if (this.index_x == 4 && this.index_y == 0) { // checking if the king is at his initial position
					if (this.currentBoard.getBoardModel()[0][0] != null && this.currentBoard.getBoardModel()[0][0].getPieceChar() == 'r') { // checking if the right rook is at his initial position
						if(rookHasNotMoved(0,0)) {
							if (this.isSafe(this.index_x, this.index_y)[this.index_x][this.index_y]) { // checking if the king not in a position of danger before doing the castling
								if (isOpenSpot(this.index_x - 1, this.index_y)) { // checking if no pieces on the way between the king and the rook + if the king doesn't put himself in danger while passing
									if (this.isSafe(this.index_x-1, this.index_y)[this.index_x - 1][this.index_y]) {
										if (isOpenSpot(this.index_x - 2, this.index_y)) {
											if (this.isSafe(this.index_x-2, this.index_y)[this.index_x - 2][this.index_y]) { // checking that king doesn't put himself in danger after doing the castling
												if(isOpenSpot(this.index_x - 3, this.index_y)){
													if(this.isSafe(this.index_x-3, this.index_y)[this.index_x - 3][this.index_y]){
														valid_moves[2][0] = true;
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return valid_moves;
	}

	/**
	 * method that determines if the rook has moved or not for castling purposes
	 * @param x initial x position of a rook
	 * @param y initial y position of a rook
	 * @return call on the actual rook to see if it has moved yet
	 */
	public boolean rookHasNotMoved(int x, int y){
		RookPiece piece = (RookPiece) this.currentBoard.getField()[x][y];
		return piece.isHasNotMoved();
	}
}