package model;

import model.pieces.*;

/**
 * Class responsible for updating the current state of the board.
 */
public class BoardUpdater {

    private Board boardModel;

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
        addPiece(0, 0, new Rook(false));
        addPiece(0, 1, new Knight(false));
        addPiece(0, 2, new Bishop(false));
        addPiece(0, 3, new Queen(false));
        addPiece(0, 4, new King(false));
        addPiece(0, 5, new Bishop(false));
        addPiece(0, 6, new Knight(false));
        addPiece(0, 7, new Rook(false));
        for (int i = 0; i < 8; i++)
            addPiece(1, i, new Pawn(false));

        // White side
        addPiece(7, 0, new Rook(true));
        addPiece(7, 1, new Knight(true));
        addPiece(7, 2, new Bishop(true));
        addPiece(7, 3, new King(true));
        addPiece(7, 4, new Queen(true));
        addPiece(7, 5, new Bishop(true));
        addPiece(7, 6, new Knight(true));
        addPiece(7, 7, new Rook(true));
        for (int i = 0; i < 8; i++)
            addPiece(6, i, new Pawn(true));
    }

    /**
     * Adds a piece to a board.
     * @param x
     * @param y
     * @param piece
     */
    public void addPiece(int x, int y, Piece piece) {
        boardModel.getField()[x][y] = piece;
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
        Piece targetPiece = boardModel.getField()[xFrom][yFrom];
        boardModel.getField()[xTo][yTo] = targetPiece;
        boardModel.getField()[xFrom][yFrom] = null;
    }
}
