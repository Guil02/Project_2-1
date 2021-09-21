package model.pieces;

import model.Board;

//TODO add en passant rule
//TODO add promotion

/**
 * Pawn piece
 */
public class PawnPiece extends ChessPiece {

    public PawnPiece(boolean white, Board board, int index_x, int index_y) {
        super(white, index_x, index_y, board);
    }

    public char getPieceChar() {
        if (this.isWhite)
            return 'P';
        else
            return 'p';
    }

    public boolean[][] validMoves() {

        boolean[][] valid_moves = new boolean[8][8];

        if(isTurn()){

            if(isWhite){
                if(withinBounds(index_y,-1)&&isOpenSpot(index_x,index_y-1)&&!checkForEnemyPiece(index_x,index_y-1)){
                    if(index_y==6&&isOpenSpot(index_x,index_y-2)&&!checkForEnemyPiece(index_x,index_y-2)){
                        valid_moves[index_x][index_y-1]=true;
                        valid_moves[index_x][index_y-2]=true;
                    }
                    else{
                        valid_moves[index_x][index_y-1]=true;
                    }
                }

                if(withinBounds(index_x,-1)&&withinBounds(index_y,-1)&&isOpenSpot(index_x-1,index_y-1)&&checkForEnemyPiece(index_x-1,index_y-1)){
                    valid_moves[index_x-1][index_y-1]=true;
                }
                if(withinBounds(index_x,1)&&withinBounds(index_y,-1)&&isOpenSpot(index_x+1,index_y-1)&&checkForEnemyPiece(index_x+1,index_y-1)){
                    valid_moves[index_x+1][index_y-1]=true;
                }
            }
            else{
                if(withinBounds(index_y, 1)&&isOpenSpot(index_x,index_y+1)&&!checkForEnemyPiece(index_x,index_y+1)){
                    if(index_y==1&&isOpenSpot(index_x,index_y+2)&&!checkForEnemyPiece(index_x,index_y+2)){
                        valid_moves[index_x][index_y+1]=true;
                        valid_moves[index_x][index_y+2]=true;
                    }
                    else{

                        valid_moves[index_x][index_y+1]=true;
                    }
                }
                if(withinBounds(index_x,-1)&&withinBounds(index_y,1)&&isOpenSpot(index_x-1,index_y+1)&&checkForEnemyPiece(index_x-1,index_y+1)){
                    valid_moves[index_x-1][index_y+1]=true;
                }
                if(withinBounds(index_x,1)&&withinBounds(index_y,1)&&isOpenSpot(index_x+1,index_y+1)&&checkForEnemyPiece(index_x+1,index_y+1)){
                    valid_moves[index_x+1][index_y+1]=true;
                }
            }
        }
        
        
        
        // prise en passant
        // update prise en passant value --> move(this.index_y + 2?)
        if(Board.priseEnPassantAuthorized > -1) {
        	if(this.index_x + 1 == Board.priseEnPassantAuthorized || this.index_x - 1 == Board.priseEnPassantAuthorized) {
        		if(isWhite = true) {
        			valid_moves[2][Board.priseEnPassantAuthorized] = true;
        		}
        		else {
        			valid_moves[6][Board.priseEnPassantAuthorized] = true;
        		}
        	}
        }
        return valid_moves;
    }
}