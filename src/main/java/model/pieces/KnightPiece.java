package model.pieces;

import model.Board;

/**
 * class that determines every valid moves for a knight
 */
public class KnightPiece extends ChessPiece {

    /**
     * constructor that creates a knight chess piece
     */
    public KnightPiece(boolean white, Board board, int index_x, int index_y) {
        super(white, index_x, index_y, board);
    }

    public char getPieceChar() {
        if (this.isWhite)
            return 'N';
        else
            return 'n';
    }

    /*
     * method that returns all possible positions for a knight to move to
     */
    public boolean[][] validMoves() {

        boolean[][] valid_moves = new boolean[Board.getBoardSize()][8];
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
        if(checkAllFalse(valid_moves)){ // if there are no valid moves for the current state of the board
            hasValidMove = false;
        }
        return valid_moves;
    }
}