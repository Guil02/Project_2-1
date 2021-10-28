package controller;

import model.pieces.*;

import java.util.Arrays;

public class BoardUpdater {


    public static void fillGameStart(Board board) {
        // Black side
        addPiece(board, new RookPiece(false, 0, 0));
        addPiece(board, new KnightPiece(false, 1, 0));
        addPiece(board, new BishopPiece(false, 2, 0));
        addPiece(board, new QueenPiece(false, 3, 0));
        addPiece(board, new KingPiece(false, 4, 0));
        addPiece(board, new BishopPiece(false, 5, 0));
        addPiece(board, new KnightPiece(false, 6, 0));
        addPiece(board, new RookPiece(false, 7, 0));
        for (int i = 0; i < 8; i++)
            addPiece(board, new PawnPiece(false, i, 1));

        // White side
        addPiece(board, new RookPiece(true, 0, 7));
        addPiece(board, new KnightPiece(true, 1, 7));
        addPiece(board, new BishopPiece(true, 2, 7));
        addPiece(board, new QueenPiece(true, 3, 7));
        addPiece(board, new KingPiece(true, 4, 7));
        addPiece(board, new BishopPiece(true, 5, 7));
        addPiece(board, new KnightPiece(true, 6, 7));
        addPiece(board, new RookPiece(true, 7, 7));
        for (int i = 0; i < 8; i++)
            addPiece(board, new PawnPiece(true, i, 6));
    }

    /**
     * Adds a piece to a board.
     * @param piece
     */
    public static void addPiece(Board board, ChessPiece piece) {
        board.getBoardModel()[piece.getX()][piece.getY()]=piece;
    }

    /**
     * Removes a piece from the board.
     */
    public static void removePiece(Board board, int x, int y) {
        board.getBoardModel()[x][y]=null;
    }

    public static void capturePiece(Board board, int x, int y){
        if(board.getPieceOffField(x,y)!=null) {
            if (board.getPieceOffField(x,y).getPieceChar() == 'K') {
                board.getBoardModel()[x][y] = null;
                if(Board.GUI_ON && board.isOriginal()){
                    board.getGraphicsConnector().setWin(false);
                }
                board.setGameOver(true);
            } else if (board.getPieceOffField(x,y).getPieceChar() == 'k') {
                board.getBoardModel()[x][y] = null;
                if(Board.GUI_ON && board.isOriginal()){
                    board.getGraphicsConnector().setWin(true);
                }
                board.setGameOver(true);
            }
        }
    }

    public static void captureEnPassantField(Board board, int x, int y){
        if(board.getPieceOffField(x,y)!=null){
            board.getBoardModel()[x][y]=null;
        }
    }

    public static void movePiece(Board board, int xFrom, int yFrom, int xTo, int yTo) {
        ChessPiece pieceToMove = board.getPieceOffField(xFrom, yFrom);
        pieceToMove.move(board, xTo,yTo);
        capturePiece(board, xTo, yTo);
        removePiece(board, xFrom, yFrom);
        addPiece(board, pieceToMove);
        board.changeTurn();
        if(!board.getGameOver()&& board.isOriginal()) {
            startPromotionDialog(board, pieceToMove, xTo, yTo);
        }
    }

    public static void startPromotionDialog(Board board, ChessPiece targetPiece, int xTo, int yTo) {
        if(targetPiece.isOnOppositeRow(yTo) && (targetPiece.getPieceChar()=='p' || targetPiece.getPieceChar()=='P')){
            board.getGraphicsConnector().startPromotionDialog(targetPiece.isWhite(), board, xTo, yTo);
        }
    }
    public static void doPromotion(Board board, ChessPiece piece){
        removePiece(board, piece.getX(),piece.getY());
        addPiece(board, piece);
        board.getGraphicsConnector().updateImages();
    }


}
