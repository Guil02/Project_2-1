// TODO Rook moves
package model.pieces;

import controller.Board;

/**
 * class that determines every valid moves for a rook
 */
public class RookPiece extends ChessPiece {
    private boolean hasNotMoved = true;

    /**
     * constructor that creates a rook chess piece
     */
    public RookPiece(boolean white, int x, int y) {
        super(white, x, y,4);
    }

    public char getPieceChar() {
        if (this.isWhite)
            return 'R';
        else
            return 'r';
    }

    @Override
    public void move(Board board, int new_x, int new_y) {
        this.hasNotMoved=false;
        board.setEnPassantActive(false);
        super.move(board, new_x, new_y);
    }

    /**
     * method that determines if the piece in question has already moved or not
     */
    public boolean getHasNotMoved() {
        return hasNotMoved;
    }

    /*
     * method that returns all possible positions for a rook to move to
     */
    public boolean[][] validMoves(Board board) {

        boolean[][] validMoves = new boolean[Board.getBoardSize()][Board.getBoardSize()];

        if(!isTurn(board)) {
            return validMoves;
        }

        int[] directions = {-8,-1,1,8};
        for(int i = 0; i< directions.length; i++){
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
        if(checkAllFalse(validMoves)){
            setHasValidMove(false);
        }
        return validMoves;
    }

    @Override
    public ChessPiece copy() {
        RookPiece rookPiece = new RookPiece(isWhite, x, y);
        rookPiece.hasNotMoved = hasNotMoved;
        rookPiece.hasValidMove = hasValidMove;
        return rookPiece;
    }

    public void setHasNotMoved(boolean hasNotMoved) {
        this.hasNotMoved = hasNotMoved;
    }
}


