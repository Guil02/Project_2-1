package model.pieces;

import controller.Board;

/**
 * class that determines every valid moves for a bishop
 */
public class BishopPiece extends ChessPiece {

    /**
     * constructor that creates a bishop chess piece
     */
    public BishopPiece(boolean isWhite, int x, int y) {
        super(isWhite, x, y, 3);
    }

    /**
     * @param board
     * @param xTo   next x position after doing the move
     * @param yTo   next y position after doing the move
     */
    @Override
    public void move(Board board, int xTo, int yTo) {
        ChessPiece.setEnPassantActive(false);
        super.move(board, xTo, yTo);
    }

    /*
     * method that returns all possible positions for a bishop to move to
     */
    public boolean[][] validMoves(Board board) {

        boolean[][] validMoves = new boolean[Board.getBoardSize()][Board.getBoardSize()];

        if(!isTurn(board)) {
            return validMoves;
        }

        int[] directions = {-9,-7,7,9};
        for(int i = 0; i<directions.length; i++){
            for(int j = 0; j<maxAmountOfSquaresToEdge(x,y,directions[i]); j++){
                int oneVar = y*8+x;
                int goal = oneVar + directions[i] * (j+1);
                int xTo = goal % 8;
                int yTo = (goal-xTo)/8;

                if(!withinBoundsOneVariable(xTo)||!withinBoundsOneVariable(yTo)){
                    break;
                }

                if(checkForOwnPiece(board,xTo, yTo)){
                    break;
                }

                validMoves[xTo][yTo]=true;

                if(checkForEnemyPiece(board, xTo, yTo)){
                    break;
                }
            }
        }

        setHasValidMove(true);
        if(checkAllFalse(validMoves)){ // if there are no valid moves for the current state of the board
            setHasValidMove(false);
        }
        return validMoves;
    }

    public char getPieceChar(){
        if (this.isWhite)
            return 'B';
        else
            return 'b';

    }

    @Override
    public ChessPiece copy() {
        BishopPiece bishopPiece = new BishopPiece(isWhite, x, y);
        bishopPiece.hasValidMove = hasValidMove;
        return bishopPiece;
    }
}