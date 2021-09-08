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

    public GameRunner() {
        init();
    }

    public void init() {
        Board board = new Board();
        BoardUpdater boardU = new BoardUpdater(board);
        boardU.fillGameStart();
        board.printBoard();
    }
}
