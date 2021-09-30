package model.pieces;

import model.Board;

/**
 * Knight piece
 */
public class KnightPiece extends ChessPiece {

    public KnightPiece(boolean white, Board board, int index_x, int index_y) {
        super(white, index_x, index_y, board);
    }

    public char getPieceChar() {
        if (this.isWhite)
            return 'N';
        else
            return 'n';
    }

    public boolean[][] validMoves() {

        boolean[][] valid_moves = new boolean[8][8];
        if(isTurn()){
            if(withinBounds(index_x,2)&&withinBounds(index_y,1)&&isOpenSpot(index_x+2,index_y+1)){
                valid_moves[index_x+2][index_y+1]=true;
                hasValidMove = true;
            }
            if(withinBounds(index_x,2)&&withinBounds(index_y,-1)&&isOpenSpot(index_x+2,index_y-1)){
                valid_moves[index_x+2][index_y-1]=true;
                hasValidMove = true;
            }
            if(withinBounds(index_x,-2)&&withinBounds(index_y,1)&&isOpenSpot(index_x-2,index_y+1)){
                valid_moves[index_x-2][index_y+1]=true;
                hasValidMove = true;
            }
            if(withinBounds(index_x,-2)&&withinBounds(index_y,-1)&&isOpenSpot(index_x-2,index_y-1)){
                valid_moves[index_x-2][index_y-1]=true;
                hasValidMove = true;
            }
            if(withinBounds(index_x,1)&&withinBounds(index_y,2)&&isOpenSpot(index_x+1,index_y+2)){
                valid_moves[index_x+1][index_y+2]=true;
                hasValidMove = true;
            }
            if(withinBounds(index_x,1)&&withinBounds(index_y,-2)&&isOpenSpot(index_x+1,index_y-2)){
                valid_moves[index_x+1][index_y-2]=true;
                hasValidMove = true;
            }
            if(withinBounds(index_x,-1)&&withinBounds(index_y,2)&&isOpenSpot(index_x-1,index_y+2)){
                valid_moves[index_x-1][index_y+2]=true;
                hasValidMove = true;
            }
            if(withinBounds(index_x,-1)&&withinBounds(index_y,-2)&&isOpenSpot(index_x-1,index_y-2)){
                valid_moves[index_x-1][index_y-2]=true;
                hasValidMove = true;
            }
        }
        if(checkAllFalse(valid_moves)){
            hasValidMove = false;
        }
        return valid_moves;
    }
}