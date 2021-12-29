package model.pieces;

import controller.Board;

/**
 * class that determines every valid moves for a knight
 */
public class KnightPiece extends ChessPiece {

    /**
     * constructor that creates a knight chess piece
     */
    public KnightPiece(boolean white, int x, int y) {
        super(white, x, y,2);
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
            return 'N';
        else
            return 'n';
    }

    /*
     * method that returns all possible positions for a knight to move to
     */
    public boolean[][] validMoves(Board board) {

        boolean[][] validMoves = new boolean[Board.getBoardSize()][8];
        if (!isTurn(board)) {
            return validMoves;
        }

        if (withinBounds(x, 2) && withinBounds(y, 1) && (isOpenSpot(board,x + 2, y + 1)||checkForEnemyPiece(board,x + 2, y + 1))) {
            validMoves[x + 2][y + 1] = true;
            setHasValidMove(true);
        }
        if (withinBounds(x, 2) && withinBounds(y, -1) && (isOpenSpot(board,x + 2, y - 1)||checkForEnemyPiece(board,x + 2, y - 1))) {
            validMoves[x + 2][y - 1] = true;
            setHasValidMove(true);
        }
        if (withinBounds(x, -2) && withinBounds(y, 1) && (isOpenSpot(board,x - 2, y + 1)||checkForEnemyPiece(board,x - 2, y + 1))) {
            validMoves[x - 2][y + 1] = true;
            setHasValidMove(true);
        }
        if (withinBounds(x, -2) && withinBounds(y, -1) && (isOpenSpot(board,x - 2, y - 1)||checkForEnemyPiece(board,x - 2, y - 1))) {
            validMoves[x - 2][y - 1] = true;
        }
        if (withinBounds(x, 1) && withinBounds(y, 2) && (isOpenSpot(board,x + 1, y + 2)||checkForEnemyPiece(board,x + 1, y + 2))) {
            validMoves[x + 1][y + 2] = true;
        }
        if (withinBounds(x, 1) && withinBounds(y, -2) && (isOpenSpot(board,x + 1, y - 2)||checkForEnemyPiece(board,x + 1, y - 2))) {
            validMoves[x + 1][y - 2] = true;
        }
        if (withinBounds(x, -1) && withinBounds(y, 2) && (isOpenSpot(board,x - 1, y + 2)||checkForEnemyPiece(board,x - 1, y + 2))) {
            validMoves[x - 1][y + 2] = true;
        }
        if (withinBounds(x, -1) && withinBounds(y, -2) && (isOpenSpot(board,x - 1, y - 2)||checkForEnemyPiece(board,x - 1, y - 2))) {
            validMoves[x - 1][y - 2] = true;
        }

        setHasValidMove(true);
        if (checkAllFalse(validMoves)) {
            setHasValidMove(false);
        }
        return validMoves;
    }

    @Override
    public ChessPiece copy() {
        KnightPiece knightPiece = new KnightPiece(isWhite, x, y);
        knightPiece.hasValidMove = hasValidMove;
        return knightPiece;
    }
}