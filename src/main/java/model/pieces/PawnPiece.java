package model.pieces;


import controller.Board;
import controller.BoardUpdater;

/**
 * class that determines every valid moves for a pawn
 */
public class PawnPiece extends ChessPiece {
    public static int getEnPassantColumn() {
        return enPassantColumn;
    }

    private static int enPassantColumn;


    /**
     * constructor that creates a pawn chess piece
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

    public boolean firstMove = true;

    /**
     * @param xTo next x position after doing the move
     * @param yTo next y position after doing the move
     */
    @Override
    public void move(Board board, int xTo, int yTo) {
        if(ChessPiece.isEnPassantActive() && xTo == enPassantColumn){
            if(isWhite() && yTo == 2){
                BoardUpdater.captureEnPassantField(board, xTo, yTo+1);
            }
            else if(!isWhite() && yTo == 5){
                BoardUpdater.captureEnPassantField(board, xTo, yTo-1);
            }
        }

        ChessPiece.setEnPassantActive(false);

        if(isWhite()){
            if(y == 6 && yTo == 4){
                ChessPiece.setEnPassantActive(true);
                enPassantColumn = x;
            }
        }
        else if(!isWhite()){
            if(y==1 && yTo == 3){
                enPassantColumn = x;
                ChessPiece.setEnPassantActive(true);
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

    /*
     * method that returns all possible positions for a pawn to move to
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
        if(ChessPiece.isEnPassantActive()){
            if(x+1 == enPassantColumn|| x-1 == enPassantColumn){
                if(isWhite() && y == 3){
                    if(isOpenSpot(board,enPassantColumn, 2)){
                        validMoves[enPassantColumn][2]=true;
                    }
                }
                else if(!isWhite() && y == 4){
                    if(isOpenSpot(board,enPassantColumn, 5)){
                        validMoves[enPassantColumn][5]=true;
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

    public static void setEnPassantColumn(int enPassantColumn) {
        PawnPiece.enPassantColumn = enPassantColumn;
    }
}