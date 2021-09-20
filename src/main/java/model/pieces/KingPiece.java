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
        return valid_moves;
    }
}