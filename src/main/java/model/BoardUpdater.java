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
        // White side
        addPiece(0, 0, new RookPiece(true));
        addPiece(0, 1, new KnightPiece(true));
        addPiece(0, 2, new BishopPiece(true));
        addPiece(0, 3, new QueenPiece(true));
        addPiece(0, 4, new KingPiece(true));
        addPiece(0, 5, new BishopPiece(true));
        addPiece(0, 6, new KnightPiece(true));
        addPiece(0, 7, new RookPiece(true));
        for (int i = 0; i < 8; i++)
            addPiece(1, i, new PawnPiece(true));

        // Black side
        addPiece(7, 0, new RookPiece(false));
        addPiece(7, 1, new KnightPiece(false));
        addPiece(7, 2, new BishopPiece(false));
        addPiece(7, 3, new QueenPiece(false));
        addPiece(7, 4, new KingPiece(false));
        addPiece(7, 5, new BishopPiece(false));
        addPiece(7, 6, new KnightPiece(false));
        addPiece(7, 7, new RookPiece(false));
        for (int i = 0; i < 8; i++)
            addPiece(6, i, new PawnPiece(false));
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
