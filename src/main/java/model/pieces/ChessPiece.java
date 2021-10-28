package model.pieces;

import controller.Board;

/**
 * Abstract class representing a chess piece
 */
public abstract class ChessPiece {

    // variables
    protected boolean isWhite;
    protected boolean hasValidMove = false;
    private static boolean enPassantActive = false;
    public boolean hasValidMove() {
        return hasValidMove;
    }
    protected int x;
    protected int y;
    private final int pieceType;



    /**
     * Constructor
     * @param isWhite
     * @param x
     * @par
     */
    public ChessPiece(boolean isWhite, int x, int y, int pieceType) {
        this.isWhite = isWhite;
        this.x = x;
        this.y = y;
        this.pieceType = pieceType;
    }

    /**
     * method that checks if no valid move exists
     * @param validMoves
     * @return true when no valid moves exist
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

    public void setHasValidMove(boolean hasValidMove) {
        this.hasValidMove = hasValidMove;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isWhite() {
        return this.isWhite;
    }

    /**
     * @param xTo next x position after doing the move
     * @param yTo next y position after doing the move
     */
    public void move(Board board, int xTo, int yTo) {
        System.out.println(enPassantActive);
        this.x = xTo;
        this.y = yTo;
    }

    /**
     * method that checks if another piece on a different field has the same colour (own team)
     * @param x
     * @param y
     */
    public boolean checkForOwnPiece(Board board, int x, int y){
        return (board.getPieceOffField(x,y) != null && board.getPieceOffField(x,y).isWhite == this.isWhite);
    }

    /**
     * method that checks if another piece on a different field has a different colour (enemy team)
     * @param x
     * @param y
     */
    public boolean checkForEnemyPiece(Board board, int x, int y) {
        return (board.getPieceOffField(x,y) != null && board.getPieceOffField(x,y).isWhite != this.isWhite);
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

    public boolean isOpenSpot(Board board, int x, int y) {
        return (!checkForOwnPiece(board, x,y)&&!checkForEnemyPiece(board,x,y));
    }



    public boolean isOnOppositeRow(int y) {
        if(isWhite){
            return y == 0;
        }
        else return y == 7;
    }

    /**
     * method that determines who's turn is to play
     * @return white or black moves
     */
    public boolean isTurn(Board board) {
        if(isWhite) {
            return board.getWhiteMove();
        }
        else return !board.getWhiteMove();
    }

    public int maxAmountOfSquaresToEdge(int x, int y, int direction){
        int a=0;
        switch(direction){
            case 9:
                a = Math.min(7-x, 7-y);
                break;
            case 7:
                a = Math.min(x, 7-y);
                break;
            case -7:
                a = Math.min(7-x, y);
                break;
            case -9:
                a = Math.min(x, y);
                break;
            case 8:
                a = 7-y;
                break;
            case 1:
                a = 7-x;
                break;
            case -1:
                a = x;
                break;
            case -8:
                a = y;
                break;
        }
        return a;

    }

    public int getPieceType() {
        return pieceType;
    }

    public static boolean isEnPassantActive() {
        return enPassantActive;
    }

    public static void setEnPassantActive(boolean enPassantActive) {
        ChessPiece.enPassantActive = enPassantActive;
    }

    public abstract boolean[][] validMoves(Board board);
    public abstract char getPieceChar();
    public abstract ChessPiece copy();
}
