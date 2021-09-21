//TODO check values 
package model.pieces;

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
    
    
    /*
     * method checking if the king doesn't put himslef in danger 
     */
    private boolean[][] isSafe(int index_x, int index_y) {
    	
    	int oldIndex_x = this.index_x; // memorizing King current position
    	int oldIndex_y = this.index_y;
    	
    	this.index_x = index_x; // simulating possible King's moves
    	this.index_y = index_y;
    	
    	Board.boardModel[index_x][index_y] =  Board.boardModel[oldIndex_x][oldIndex_y]; // updating the new King position into the board
    	Board.boardModel[oldIndex_x][oldIndex_y] = null;
    	
    	ArrayList<ChessPieces> ennemies; // storing current piece threatning ennemies
    	
    	if(this.isWhite == true) { 
    		
    		ennemies = board.getBlackPieces();
    	}
    	else {
    		ennemies = board.getWhitePieces();
        	
    	}
    	
    	boolean[][] isSafe = new boolean[Board.BOARDSIZE][Board.BOARDSIZE];
    	
    	for(int i = 0; i<isSafe.length; i++) { // setting all moves being safe by default 
    		for(int j = 0; j<isSafe[i].length; j++) {
    			isSafe[i][j] = true;
    		}
    	}
    		
    	for(int i = 0; i<ennemies.size(); i++) {
    		
    		boolean[][] tmp = ennemies[i].validMoves(); // checking if ennemie is threatning 
    		
    		for(int j = 0; j<isSafe.length; j++) {
    			for(int k = 0; k<isSafe[j].length; k++) {
    				if(tmp[j][k] == true) {
    					
    					isSafe[j][k] == false;   				}
    				}
    			}
    		}
    	}	
    	
    	boolean[][] isSafe = tmp;
    	
    	
    	this.index_x = oldIndex_x; // King returning to initial position 
    	this.index_y = oldIndex_y;
    	
    	Board.boardModel[oldIndex_x][oldIndex_y] =  Board.boardModel[index_x][index_y]; // reupdating the board as it originally was
    	Board.boardModel[index_x][index_y] = null;
    	
    	
    	return isSafe;
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
        if(this.isWhite == true) {
        	if(this.index_x == 4 && this.index_y == 7) { //checking if the king is at his initial position
        		if(Board.board[7][7] != null && Board.board[7][7].getPieceChar() == 'R') { //checking if the right rook is at his initial position
        			if(this.isSafe(this.index_x, this.index_y)) { //checking if the king not in a position of danger before doing the rook
        				if(isOpenSpot(this.index_x + 1, this.index_y) == true) { //checking if no pieces on the way between the king and the rook + if the king doesn't put himself in danger while passing
        					if(this.isSafe(this.index_x + 1, this.index_y)) {
        						if(isOpenSpot(this.index_x + 2, this.index_y) == true) {
        							if(this.isSafe(this.index_x + 2, this.index_y)) {
        								if(this.isSafe(this.index_x + 3, this.index_y)) { //checking that king doesn't put himself in danger after doing the rook
        									valid_moves[7][7] = true;
        								}
        							}
        						}
        					}
        				}
        			}
        		}
        	}
        }
        else {
        	if(this.index_x == 4 && this.index_y == 1) { //checking if the king is at his initial position
        		if(Board.board[0][0] != null && Board.board[0][0].getPieceChar() == 'R') { //checking if the right rook is at his initial position
        			if(this.isSafe(this.index_x, this.index_y)) { //checking if the king not in a position of danger before doing the rook
        				if(isOpenSpot(this.index_x - 1, this.index_y) == true) { //checking if no pieces on the way between the king and the rook + if the king doesn't put himself in danger while passing
        					if(this.isSafe(this.index_x - 1, this.index_y)) {
        						if(isOpenSpot(this.index_x - 2, this.index_y) == true) {
        							if(this.isSafe(this.index_x - 2, this.index_y)) {
        								if(this.isSafe(this.index_x - 3, this.index_y)) {
        									valid_moves[0][0] = true;
        								}
        							}
        						}
        					}
        				}
        			}
        		}
        	}
        }
        
        //great rook
        if(this.isWhite == true) {
        	if(this.index_x == 4 && this.index_y == 7) { //checking if the king is at his initial position
        		if(Board.board[0]0] != null && Board.board[0][0].getPieceChar() == 'R') { //checking if the right rook is at his initial position
        			if(this.isSafe(this.index_x, this.index_y)) { //checking if the king not in a position of danger before doing the rook
        				if(isOpenSpot(this.index_x - 1, this.index_y) == true) { //checking if no pieces on the way between the king and the rook + if the king doesn't put himself in danger while passing
        					if(this.isSafe(this.index_x - 1, this.index_y)) {
        						if(isOpenSpot(this.index_x - 2, this.index_y) == true) {
        							if(this.isSafe(this.index_x - 2, this.index_y)) {
        								if(isOpenSpot(this.index_x - 3, this.index_y) == true) {
        									if(this.isSafe(this.index_x - 3, this.index_y)) {
        										if(this.isSafe(this.index_x - 4, this.index_y)) { //checking that king doesn't put himself in danger after doing the rook
        											valid_moves[0][0] = true;
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
        else {
        	if(this.index_x == 4 && this.index_y == 1) { //checking if the king is at his initial position
        		if(Board.board[0][7] != null && Board.board[0][1].getPieceChar() == 'R') { //checking if the right rook is at his initial position
        			if(this.isSafe(this.index_x, this.index_y)) { //checking if the king not in a position of danger before doing the rook
        				if(isOpenSpot(this.index_x + 1, this.index_y) == true) { //checking if no pieces on the way between the king and the rook + if the king doesn't put himself in danger while passing
        					if(this.isSafe(this.index_x + 1, this.index_y)) {
        						if(isOpenSpot(this.index_x + 2, this.index_y) == true) {
        							if(this.isSafe(this.index_x + 2, this.index_y)) {
        								if(isOpenSpot(this.index_x + 3, this.index_y) == true) {
        									if(this.isSafe(this.index_x + 3, this.index_y)) {
        										if(this.isSafe(this.index_x + 4, this.index_y)) { //checking that king doesn't put himself in danger after doing the rook
        											valid_moves[7][1] = true;
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
 	
}