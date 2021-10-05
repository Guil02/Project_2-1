package model;

import controller.GameRunner;
import model.pieces.*;

/**
 * Class responsible for updating the current state of the board.
 */
public class BoardUpdater {

    private Board boardModel;
    private char currentMove;
    /**
     * Constructor
     * @param boardModel
     */
    public BoardUpdater(Board boardModel) {
        this.boardModel = boardModel;
    }

    /**
     * Fills the board for a default start of the game.
     * Can also be used to reset a game.
     */
    public void fillGameStart() {
        // Black side
        addPiece(new RookPiece(false, boardModel, 0, 0));
        addPiece(new KnightPiece(false, boardModel, 1, 0));
        addPiece(new BishopPiece(false, boardModel, 2, 0));
        addPiece(new QueenPiece(false, boardModel, 3, 0));
        addPiece(new KingPiece(false, boardModel, 4, 0));
        addPiece(new BishopPiece(false, boardModel, 5, 0));
        addPiece(new KnightPiece(false, boardModel, 6, 0));
        addPiece(new RookPiece(false, boardModel, 7, 0));
        for (int i = 0; i < 8; i++)
            addPiece(new PawnPiece(false, boardModel, i, 1));

        // White side
        addPiece(new RookPiece(true, boardModel, 0, 7));
        addPiece(new KnightPiece(true, boardModel, 1, 7));
        addPiece(new BishopPiece(true, boardModel, 2, 7));
        addPiece(new QueenPiece(true, boardModel, 3, 7));
        addPiece(new KingPiece(true, boardModel, 4, 7));
        addPiece(new BishopPiece(true, boardModel, 5, 7));
        addPiece(new KnightPiece(true, boardModel, 6, 7));
        addPiece(new RookPiece(true, boardModel, 7, 7));
        for (int i = 0; i < 8; i++)
            addPiece(new PawnPiece(true, boardModel, i, 6));
    }

    /**
     * Adds a piece to a board.
     * @param piece
     */
    public void addPiece(ChessPiece piece) {
        boardModel.getField()[piece.getIndex_x()][piece.getIndex_y()] = piece;
        boardModel.addBlackOrWhite(piece);
    }

    /**
     * Removes a piece from the board.
     */
    public void removePiece(int x, int y) {
        boardModel.getField()[x][y] = null;
    }

    /**
     *
     * @param xFrom
     * @param yFrom
     * @param xTo
     * @param yTo
     */
    public void movePiece(int xFrom, int yFrom, int xTo, int yTo) {
        ChessPiece targetPiece = boardModel.getField()[xFrom][yFrom];
        targetPiece.move(xTo,yTo);
        boardModel.getField()[xTo][yTo] = targetPiece;
        boardModel.getField()[xFrom][yFrom] = null;
        boardModel.doMove();

        boardModel.checkForTakenPieces();
        //TODO: make this a prompt
        promotion(targetPiece, xTo, yTo);
    }

    private void promotion(ChessPiece targetPiece, int xTo, int yTo) {
        if((targetPiece.getPieceChar()=='p' || targetPiece.getPieceChar()=='P') && targetPiece.isOnOppositeRow(xTo, yTo)){
            removePiece(xTo,yTo);
            addPiece(new QueenPiece(targetPiece.isWhite(), boardModel, xTo, yTo));
        }
    }
}