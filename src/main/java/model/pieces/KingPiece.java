package model.pieces;

import controller.Board;
import controller.BoardUpdater;

import java.util.*;

/**
 * class that determines every valid moves for a king
 */
public class KingPiece extends ChessPiece {
    private boolean hasNotMoved = true;

    public KingPiece(boolean white, int x, int y) {
        super(white, x, y, 6);
    }

    public char getPieceChar() {
        if (this.isWhite)
            return 'K';
        else
            return 'k';
    }

    @Override
    public void move(Board board, int new_x, int new_y) {
        this.hasNotMoved = false;
        ChessPiece.setEnPassantActive(false);
        if(new_x == 2 && x==4){
            BoardUpdater.movePiece(board, 0,new_y,3,new_y);
        }
        else if(new_x == 6 && x == 4){
            BoardUpdater.movePiece(board, 7,new_y, 5,new_y);
        }
        super.move(board, new_x, new_y);
    }

    public boolean[][] validMoves(Board board) {

        boolean[][] validMoves = new boolean[Board.getBoardSize()][Board.getBoardSize()];

        if (!isTurn(board)) {
            return validMoves;
        }

        int[] directions = {-8,-1,8,1,-9,-7,9,7};

        for(int i = 0; i<directions.length; i++){
            for(int j = 0; j<maxAmountOfSquaresToEdge(x,y,directions[i]) && j<1; j++){
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

        if(hasNotMoved){
            if(longCastleOpen(board) && rookHasNotMoved(board, x-4, y)){
                validMoves[x-2][y]=true;
            }
            if(shortCastleOpen(board) && rookHasNotMoved(board, x+3, y)){
                validMoves[x+2][y]=true;
            }
        }

        setHasValidMove(true);
        if (checkAllFalse(validMoves)) {
            setHasValidMove(false);
        }
        return validMoves;
    }

    public boolean rookHasNotMoved(Board board, int x, int y) {
        if((withinBoundsOneVariable(x)&&withinBoundsOneVariable(y))&&board.getPieceOffField(x,y)!=null && board.getPieceOffField(x,y).getPieceType()==4){
            RookPiece piece = (RookPiece) board.getPieceOffField(x,y);
            return piece.isHasNotMoved();
        }
        else return false;
    }

    public boolean longCastleOpen(Board board){
        return isOpenSpot(board, x-1, y) && isOpenSpot(board, x-2, y) && isOpenSpot(board, x-3, y);
    }

    public boolean shortCastleOpen(Board board){
        return isOpenSpot(board, x+1, y) && isOpenSpot(board, x+2, y);
    }

    @Override
    public ChessPiece copy() {
        KingPiece kingPiece = new KingPiece(isWhite, x, y);
        kingPiece.hasValidMove = hasValidMove;
        kingPiece.hasNotMoved = hasNotMoved;
        return kingPiece;
    }
}
