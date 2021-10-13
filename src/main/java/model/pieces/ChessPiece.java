package model.pieces;

import model.Board;

/**
 * Abstract class representing a chess piece
 */
public abstract class ChessPiece {

    // variables
    protected boolean isWhite;
    protected boolean hasValidMove;
    public boolean hasValidMove() {
        return hasValidMove;
    }
    protected int index_x;
    protected int index_y;
    protected Board currentBoard;

    /**
     * empty constructor
     */
    public ChessPiece() { }

    /**
     * Constructor
     * @param isWhite
     * @param index_x
     * @param index_y
     * @param board current board
     */
    public ChessPiece(boolean isWhite, int index_x, int index_y, Board board) {
        this.isWhite = isWhite;
        this.index_x = index_x;
        this.index_y = index_y;
        this.currentBoard = board;

    }

    /**
     * method that checks all false moves
     * @param validMoves
     * @return boolean true when it's done
     */
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

    /**
     * move method to check if en passant/castling conditions are reunited to authorize en passant/castling
     * @param new_index_x next x position after doing the move
     * @param new_index_y next y position after doing the move
     */
    public void move(int new_index_x, int new_index_y) {

        // en passant
        this.currentBoard.setEnPassantAuthorized(-1); // set en passant not authorized
        // check if a pawn has moved 2 spots from its initial position
        if( (this.getPieceChar() == 'P') || (this.getPieceChar() == 'p') ) {
            if(Math.abs(this.index_y - new_index_y) == 2) {

                this.currentBoard.setEnPassantAuthorized(this.index_x); // set en passant authorized for the enemy pawns who have the possibility to take the current pawn in en passant

            }
        }

        // pawns taking enemy pawns in en passant
        if( (this.getPieceChar() == 'P') || (this.getPieceChar() == 'p') ) {
            if( (Math.abs(this.index_x - new_index_x) == 1) && (Math.abs(this.index_y - new_index_y) == 1) ) {
                if(isOpenSpot(new_index_x, new_index_y)){

                    this.currentBoard.getBoardUpdater().removePiece(new_index_x, this.index_y); // remove automatically the enemy pawn that was taken by en passant from the board
                }
            }
        }

        // castling
        // whites
        if(this.getPieceChar() == 'K') {
            // small castling
            if(this.index_x - new_index_x == -2) { // if king moved to castling position

                currentBoard.getBoardUpdater().movePiece(7, 7, 5, 7); // move automatically the associated rook to its castling position
                this.currentBoard.getGameRunner().setWhiteMove(true);
            }
            // great castling
            else if(this.index_x - new_index_x == 2) {

                currentBoard.getBoardUpdater().movePiece(0, 7, 3, 7);
                this.currentBoard.getGameRunner().setWhiteMove(true);
            }
        }
        // same for blacks
        if(this.getPieceChar() == 'k') {
            if(this.index_x - new_index_x == -2){

                currentBoard.getBoardUpdater().movePiece(7, 0, 5, 0);
                this.currentBoard.getGameRunner().setWhiteMove(false);


            }
            else if(this.index_x - new_index_x == 2) {

                currentBoard.getBoardUpdater().movePiece(0, 0, 3, 0);
                this.currentBoard.getGameRunner().setWhiteMove(false);
            }

        }

        // update internal position
        this.index_x = new_index_x;
        this.index_y = new_index_y;
    }

    /**
     * method that checks if another piece on a different field has the same colour (own team)
     * @param index_x
     * @param index_y
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
     * method that checks if another piece on a different field has a different colour (enemy team)
     * @param index_x
     * @param index_y
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
    /**
     * method that checks if incrementing the actual x or y position by a certain value will lead the piece to remain within the bounds of the board
     * @param variable x or y parameters
     * @param increment incrementing value applied on variable
     */
    public boolean withinBounds(int variable, int increment) {
        return variable + increment < BOARDSIZE && variable + increment >= 0;
    }

    /**
     * method that checks if a single x or y position is within the bounds of the board
     * @param value x or y parameters
     */
    public boolean withinBoundsOneVariable(int value){
        return value < BOARDSIZE && value >= 0;
    }

    public boolean isOpenSpot(int x, int y) {
        if(!checkForOwnPiece(x,y)) {
            return true;
        }
        else{
            return false;
        }
    }

    public abstract boolean[][] validMoves();
    public abstract char getPieceChar();
    public boolean isOnOppositeRow(int x,int y) {
        if(isWhite){
            return y == 0;
        }
        else return y == 7;
    }

    /**
     * method that determines who's turn is to play
     * @return white or black moves
     */
    public boolean isTurn() {
        if(isWhite) {
            return currentBoard.getWhiteMove();
        }
        else return !currentBoard.getWhiteMove();
    }
}
