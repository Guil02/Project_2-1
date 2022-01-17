package model.pieces;

import controller.Board;
import controller.BoardUpdater;

/**
 * Class that determines every valid moves for a pawn.
 */
public class PawnPiece extends ChessPiece {
    public static int getEnPassantColumn(Board board) {
        return board.getEnPassantColumn();
    }

    // Variables
    public boolean firstMove = true;

    /**
     * Constructor
     */
    public PawnPiece(boolean white, int x, int y) {
        super(white, x, y,1);
        if(isWhite && y != 6){
            firstMove = false;
        }
        else if(!isWhite && y != 1){
            firstMove = false;
        }
    }

    /**
     * @param xTo next x position after doing the move
     * @param yTo next y position after doing the move
     */
    @Override
    public void move(Board board, int xTo, int yTo) {
        if(board.isEnPassantActive() && xTo == board.getEnPassantColumn()){
            if(isWhite() && yTo == 2){
                BoardUpdater.captureEnPassantField(board, xTo, yTo+1);
            }
            else if(!isWhite() && yTo == 5){
                BoardUpdater.captureEnPassantField(board, xTo, yTo-1);
            }
        }
        board.setEnPassantActive(false);
        if(isWhite()){
            if(y == 6 && yTo == 4){
                board.setEnPassantActive(true);
                board.setEnPassantColumn(x);
            }
        }
        else if(!isWhite()){
            if(y==1 && yTo == 3){
                board.setEnPassantColumn(x);
                board.setEnPassantActive(true);
            }
        }

        if(firstMove){
            firstMove=false;
        }

        super.move(board, xTo, yTo);
    }

    public char getPieceChar() {
        if (this.isWhite)
            return 'P';
        else
            return 'p';
    }

    /**
     * Method that returns all possible positions for a pawn to move to.
     */
    public boolean[][] validMoves(Board board) {
        boolean[][] validMoves = new boolean[Board.getBoardSize()][Board.getBoardSize()];
        if(!isTurn(board)) {
            return validMoves;
        }
        if(isWhite) {
            if (withinBoundsOneVariable(y-1)&&board.getPieceOffField(x, y - 1)==null){
                validMoves[x][y-1] = true;

                if(firstMove && board.getPieceOffField(x, y - 2)==null){
                    validMoves[x][y-2] = true;
                }
            }

        }
        if(!isWhite){
            if (withinBoundsOneVariable(y+1)&&board.getPieceOffField(x, y + 1)==null){
                validMoves[x][y+1] = true;

                if(firstMove && board.getPieceOffField(x, y + 2)==null){
                    validMoves[x][y+2] = true;
                }
            }

        }
        validCaptures(board, validMoves);
        enPassant(board, validMoves);
        setHasValidMove(true);
        if(checkAllFalse(validMoves)){
            setHasValidMove(false);
        }
        return validMoves;
    }

    public void validCaptures(Board board, boolean[][] validMoves){
        if(isWhite){
            if(withinBounds(x,-1)&&withinBounds(y,-1)&&checkForEnemyPiece(board, x-1, y-1)){
                validMoves[x-1][y-1]=true;
            }
            if(withinBounds(x,1)&&withinBounds(y,-1)&&checkForEnemyPiece(board, x+1, y-1)){
                validMoves[x+1][y-1]=true;
            }
        }

        if(!isWhite){
            if(withinBounds(x,-1)&&withinBounds(y,1)&&checkForEnemyPiece(board, x-1, y+1)){
                validMoves[x-1][y+1]=true;
            }
            if(withinBounds(x,1)&&withinBounds(y,1)&&checkForEnemyPiece(board, x+1, y+1)){
                validMoves[x+1][y+1]=true;
            }
        }
    }

    public void enPassant(Board board, boolean[][] validMoves){
        if(board.isEnPassantActive()){
            if(x+1 == board.getEnPassantColumn()|| x-1 == board.getEnPassantColumn()){
                if(isWhite() && y == 3){
                    if(isOpenSpot(board,board.getEnPassantColumn(), 2)){
                        validMoves[board.getEnPassantColumn()][2]=true;
                    }
                }
                else if(!isWhite() && y == 4){
                    if(isOpenSpot(board,board.getEnPassantColumn(), 5)){
                        validMoves[board.getEnPassantColumn()][5]=true;
                    }
                }
            }
        }
    }

    @Override
    public ChessPiece copy() {
        PawnPiece pawnPiece = new PawnPiece(isWhite, x, y);
        pawnPiece.hasValidMove = hasValidMove;
        pawnPiece.firstMove = firstMove;
        return pawnPiece;
    }

    public static void setEnPassantColumn(Board board, int enPassantColumn) {
        board.setEnPassantColumn(enPassantColumn);
    }
}