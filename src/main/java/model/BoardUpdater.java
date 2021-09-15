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
        addPiece(0, 0, new RookPiece(false));
        addPiece(1, 0, new KnightPiece(false));
        addPiece(2, 0, new BishopPiece(false));
        addPiece(3, 0, new QueenPiece(false));
        addPiece(4, 0, new KingPiece(false));
        addPiece(5, 0, new BishopPiece(false));
        addPiece(6, 0, new KnightPiece(false));
        addPiece(7, 0, new RookPiece(false));
        for (int i = 0; i < 8; i++)
            addPiece(i, 1, new PawnPiece(false));

        // Black side
        addPiece(0, 7, new RookPiece(true));
        addPiece(1, 7, new KnightPiece(true));
        addPiece(2, 7, new BishopPiece(true));
        addPiece(3, 7, new QueenPiece(true));
        addPiece(4, 7, new KingPiece(true));
        addPiece(5, 7, new BishopPiece(true));
        addPiece(6, 7, new KnightPiece(true));
        addPiece(7, 7, new RookPiece(true));
        for (int i = 0; i < 8; i++)
            addPiece(i, 6, new PawnPiece(true));
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
