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
    
    private boolean[][] isSafe(int index_x, int index_y) {
    	
    	int oldIndex_x = this.index_x;
    	int oldIndex_y = this.index_y;
    	
    	this.index_x = index_x;
    	this.index_y = index_y;
    	
    	
    	Board.boardModel[index_x][index_y] =  Board.boardModel[oldIndex_x][oldIndex_y];
    	Board.boardModel[oldIndex_x][oldIndex_y] = null;
    	
    	
    	ArrayList<ChessPieces> ennemies;
    	
    	if(this.isWhite == true) {
    		
    		ennemies = board.getBlackPieces();
    	}
    	else {
    		ennemies = board.getWhitePieces();
        	
    	}
    	
    	
    	boolean[][] isSafe = new boolean[Board.BOARDSIZE][Board.BOARDSIZE];
    	
    	for(int i = 0; i<isSafe.length; i++) {
    		for(int j = 0; j<isSafe[i].length; j++) {
    			isSafe[i][j] = true;
    		}
    	}
    		
    	for(int i = 0; i<ennemies.size(); i++) {
    		
    		boolean[][] tmp = ennemies[i].validMoves();
    		
    		for(int j = 0; j<isSafe.length; j++) {
    			for(int k = 0; k<isSafe[j].length; k++) {
    				if(tmp[j][k] == true) {
    					
    					isSafe[j][k] == false;   				}
    				}
    			}
    		}
    	}	
    	
    	boolean[][] isSafe = tmp;
    	
    	
    	this.index_x = oldIndex_x;
    	this.index_y = oldIndex_y;
    	
    	Board.boardModel[oldIndex_x][oldIndex_y] =  Board.boardModel[index_x][index_y];
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
        	if(this.index_x == 4 && this.index_y == 7) {
        		if(Board.board[7][7] != null && Board.board[7][7].getPieceChar() == 'R') {
        			if(this.isSafe(this.index_x, this.index_y)) {
        				if(isOpenSpot(this.index_x + 1, this.index_y) == true) {
        					if(this.isSafe(this.index_x + 1, this.index_y)) {
        						if(isOpenSpot(this.index_x + 2, this.index_y) == true) {
        							if(this.isSafe(this.index_x + 2, this.index_y)) {
        								if(this.isSafe(this.index_x + 3, this.index_y)) {
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
        
        return valid_moves;
    }
 	
}