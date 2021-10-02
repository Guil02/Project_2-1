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
        chessGUI = new ChessGUI();
        graphicsConnector = new GraphicsConnector(this);
        chessGUI.launchGUI(graphicsConnector);
    }

    public void init() {
        totalMoves = 0;
        whiteMove = true;
        board = new Board(this);
        boardUpdater = new BoardUpdater(board);
        board.setBoardUpdater(boardUpdater);
        boardUpdater.fillGameStart();
        board.printBoard();

        boolean[][] testValidMoves = board.getPiece(2,0).validMoves();
        graphicsConnector.initConnector();

        /* --> Print valid moves
        System.out.println();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (testValidMoves[j][i])
                    System.out.print("[ x ] ");
                else
                    System.out.print("[ - ] ");
            }
            System.out.println();
        }

         */
        // boardUpdater.movePiece(2, 1, 2, 3);
        // board.printBoard();
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

    public Board getBoard() {
        return board;
    }

    public BoardUpdater getBoardUpdater() {
        return boardUpdater;
    }

    public boolean getWhiteMove(){
        return whiteMove;
    }

    public void setWhiteMove(boolean whiteMove) {
        this.whiteMove = whiteMove;
    }

    public void doMove(){
        whiteMove = !whiteMove;
    }
}
