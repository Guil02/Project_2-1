package model.pieces;

import model.Board;

/**
 * class that determines every valid moves for a pawn
 */
public class PawnPiece extends ChessPiece {

    /**
     * constructor that creates a pawn chess piece
     */
    public PawnPiece(boolean white, Board board, int index_x, int index_y) {
        super(white, index_x, index_y, board);
    }

    public char getPieceChar() {
        if (this.isWhite)
            return 'P';
        else
            return 'p';
    }

    /*
     * method that returns all possible positions for a pawn to move to
     */
    public boolean[][] validMoves() {

        boolean[][] valid_moves = new boolean[Board.getBoardSize()][Board.getBoardSize()];

        if(isTurn()) {
            // whites
            if(isWhite) {
                // first move --> 2 options if conditions reunited
                if( (withinBounds(index_y,-1)) && (isOpenSpot(index_x,index_y-1)) && !(checkForEnemyPiece(index_x,index_y-1)) ) {
                    if( (index_y==6) && (isOpenSpot(index_x,index_y-2)) && !(checkForEnemyPiece(index_x,index_y-2)) ) {
                        valid_moves[index_x][index_y-1] = true; // option 1: move to 1 spot forward
                        valid_moves[index_x][index_y-2] = true; // option 2: move to 2 spots forward
                    }
                    else {
                        valid_moves[index_x][index_y-1] = true; // if piece blocking the second forward spot
                    }
                }

                // eating in diagonal
                // check if move possible and if there is an enemy piece on the target spot
                if( (withinBounds(index_x,-1)) && (withinBounds(index_y,-1)) && (isOpenSpot(index_x-1,index_y-1)) && (checkForEnemyPiece(index_x-1,index_y-1)) ) {
                    valid_moves[index_x-1][index_y-1] = true;
                }
                if( (withinBounds(index_x,1)) && (withinBounds(index_y,-1)) && (isOpenSpot(index_x+1,index_y-1)) && (checkForEnemyPiece(index_x+1,index_y-1)) ) {
                    valid_moves[index_x+1][index_y-1] = true;
                }
            }
            else{
                // same for blacks
                if( (withinBounds(index_y, 1)) && (isOpenSpot(index_x,index_y+1)) && (!checkForEnemyPiece(index_x,index_y+1)) ) {
                    if( (index_y==1) && (isOpenSpot(index_x,index_y+2)) && (!checkForEnemyPiece(index_x,index_y+2)) ) {
                        valid_moves[index_x][index_y+1] = true;
                        valid_moves[index_x][index_y+2] = true;
                    }
                    else {
                        valid_moves[index_x][index_y+1] = true;
                    }
                }
                if( (withinBounds(index_x,-1)) && (withinBounds(index_y,1)) && (isOpenSpot(index_x-1,index_y+1)) && (checkForEnemyPiece(index_x-1,index_y+1)) ) {
                    valid_moves[index_x-1][index_y+1] = true;
                }
                if( (withinBounds(index_x,1)) && (withinBounds(index_y,1)) && (isOpenSpot(index_x+1,index_y+1)) && (checkForEnemyPiece(index_x+1,index_y+1)) ) {
                    valid_moves[index_x+1][index_y+1] = true;
                }
            }
        }

        // en passant
        if(this.currentBoard.getEnPassantAuthorized() > -1) { // if en passant authorized
            if( (this.index_x + 1 == this.currentBoard.getEnPassantAuthorized()) || (this.index_x - 1 == this.currentBoard.getEnPassantAuthorized()) ) { // check if enemy pawns on left/right hand-size of the target pawn are authorized to do en passant
                if( (this.isWhite) && (this.index_y == 3) ) { // whites that moved to 2 spots forward
                    valid_moves[this.currentBoard.getEnPassantAuthorized()][2] = true;
                }
                else if( (!this.isWhite) && (this.index_y == 4) ) { // blacks that moved to 2 spots forward
                    valid_moves[this.currentBoard.getEnPassantAuthorized()][5] = true;
                }
            }
        }
        return valid_moves;
    }
}