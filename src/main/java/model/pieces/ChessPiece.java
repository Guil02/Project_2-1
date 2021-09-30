package model.pieces;

import model.Board;

/**
 * Abstract class to represent a chess piece
 */
public abstract class ChessPiece {

    // Variables
    protected boolean isWhite;
    protected boolean hasValidMove;

    public boolean hasValidMove() {
        return hasValidMove;
    }

    protected int index_x;
    protected int index_y;
    protected Board currentBoard;

    public ChessPiece() {

    }

    /**
     * Constructor
     * @param isWhite
     * @param index_x
     * @param index_y
     */
    public ChessPiece(boolean isWhite, int index_x, int index_y, Board board) {

        this.isWhite = isWhite;
        this.index_x = index_x;
        this.index_y = index_y;
        this.currentBoard = board;

    }

    public boolean checkAllFalse(boolean[][] validMoves){


        for (boolean[] validMove : validMoves) {
            for (int j = 0; j < validMoves[0].length; j++) {
                if (validMove[j]) {
                    return false;
                }
            }
        }

        return true;
    }
    public int getIndex_x() {
        return this.index_x;
    }

    public int getIndex_y() {
        return this.index_y;
    }

    public boolean isWhite() {
        return this.isWhite;
    }

    public void move(int index_x, int index_y) {

        // Updates internal position
        this.index_x = index_x;
        this.index_y = index_y;

    }

    /**
     * Checks, if another piece on a different field has the same colour. (Own team)
     * @param index_x
     * @param index_y
     * @return
     */
    public boolean checkForOwnPiece(int index_x, int index_y) {
        if (currentBoard.getPiece(index_x, index_y) == null)
            return false;
        if (currentBoard.getField()[index_x][index_y].isWhite == this.isWhite)
            return true;
        else
            return false;
    }

    /**
     * Checks, if another piece on a different field has a different colour. (Enemy team)
     * @param index_x
     * @param index_y
     * @return
     */
    public boolean checkForEnemyPiece(int index_x, int index_y) {
        if (currentBoard.getPiece(index_x, index_y) == null)
            return false;
        if (currentBoard.getField()[index_x][index_y].isWhite != this.isWhite)
            return true;
        else
            return false;
    }

    private int BOARDSIZE = Board.getBoardSize();
    public boolean withinBounds(int variable, int increment){
        return variable + increment < BOARDSIZE && variable + increment >= 0;
    }

    public boolean withinBoundsOneVariable(int value){
        return value < BOARDSIZE && value >= 0;
    }

    public boolean isOpenSpot(int x, int y){
        if(!checkForOwnPiece(x,y)){
            return true;
        }
        else{
            return false;
        }
    }

    public abstract boolean[][] validMoves();

    public abstract char getPieceChar();

    public boolean isOnOppositeRow(int x,int y){
        if(isWhite){
            return y == 0;
        }
        else return y == 7;
    }

    public boolean isTurn(){
        if(isWhite){
            return currentBoard.getWhiteMove();
        }
        else return !currentBoard.getWhiteMove();
    }
}
