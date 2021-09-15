package controller;

import gui.ChessGUI;
import model.Board;
import model.BoardUpdater;

/**
 * Main part of the backend.
 */
public class GameRunner {

    // Variables
    private int totalMoves;
    private boolean whiteMove;
    private Board board;
    private BoardUpdater boardUpdater;
    ChessGUI chessGUI;
    GraphicsConnector graphicsConnector;

    public GameRunner() {
        init();
    }

    public void init() {
        totalMoves = 0;

        board = new Board();
        boardUpdater = new BoardUpdater(board);
        boardUpdater.fillGameStart();
        board.printBoard();


        // boardUpdater.movePiece(2, 1, 2, 3);
        // board.printBoard();

        chessGUI = new ChessGUI();
        graphicsConnector = new GraphicsConnector();
        chessGUI.launchGUI(graphicsConnector);
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
        totalMoves++;
    }
}
