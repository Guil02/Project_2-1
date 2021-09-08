package controller;

import model.Board;
import model.BoardUpdater;

/**
 * Main part of the backend.
 */
public class GameRunner {

    // Variables
    private int totalMoves = 0;
    private boolean whiteMove;
    private Board board;
    private BoardUpdater boardUpdater;

    public GameRunner() {
        init();
    }

    public void init() {
        board = new Board();
        boardUpdater = new BoardUpdater(board);
        boardUpdater.fillGameStart();
        board.printBoard();

        boardUpdater.movePiece(1, 0, 3, 0);
        board.printBoard();
    }

    /**
     * Tries to move a piece from one position to the other.
     * Validity is checked in used methods.
     * @param xFrom     initial x position
     * @param yFrom     initial y position
     * @param xTo       target x position
     * @param yTo       target y position
     */
    public void attemptMove(int xFrom, int yFrom, int xTo, int yTo) {
        // Check validity

        // If check is okay, move piece to requested position
        boardUpdater.movePiece(xFrom, yFrom, xTo, yTo);
    }
}
