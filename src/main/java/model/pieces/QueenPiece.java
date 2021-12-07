package model.pieces;

import controller.Board;

/**
 * class that determines every valid moves for a queen
 */
public class QueenPiece extends ChessPiece {

    /**
     * constructor that creates a queen chess piece
     */
    public QueenPiece(boolean isWhite, int x, int y) {
        super(isWhite, x, y,5);
    }

    /**
     * @param board
     * @param xTo   next x position after doing the move
     * @param yTo   next y position after doing the move
     */
    @Override
    public void move(Board board, int xTo, int yTo) {
        board.setEnPassantActive(false);
        super.move(board, xTo, yTo);
    }

    public char getPieceChar() {
        if (this.isWhite)
            return 'Q';
        else
            return 'q';
    }

    /*
     * method that returns all possible positions for a queen to move to
     */
    public boolean[][] validMoves(Board board) {

        boolean[][] validMoves = new boolean[Board.getBoardSize()][Board.getBoardSize()];

        if(!isTurn(board)){
            return validMoves;
        }


        int[] directions = {-9,-8,-7,-1,1,7,8,9};
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

    @Override
    public ChessPiece copy() {
        QueenPiece queenPiece = new QueenPiece(isWhite, x, y);
        queenPiece.hasValidMove = hasValidMove;
        return queenPiece;
    }
}
